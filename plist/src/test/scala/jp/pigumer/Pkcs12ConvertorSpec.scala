package jp.pigumer

import java.io.{FileOutputStream, StringReader}
import java.math.BigInteger
import java.time.{Duration, Instant}
import java.util.Date

import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers
import org.bouncycastle.asn1.x500.X500NameBuilder
import org.bouncycastle.asn1.x500.style.BCStyle
import org.bouncycastle.asn1.x509.AlgorithmIdentifier
import org.bouncycastle.cert.X509v3CertificateBuilder
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter
import org.bouncycastle.crypto.util.PrivateKeyFactory
import org.bouncycastle.operator.bc.BcRSAContentSignerBuilder
import org.specs2.mutable.Specification

class Pkcs12ConvertorSpec extends Specification {

  "Pkcs12Convertor" should {

    "parsePrivateKey" in {
      val keyPair = RSAKeyPairFactory.generate
      val pem = RSAKeyPairFactory.privateKeyToString(keyPair)
      val reader = new StringReader(pem)

      val pk = Pkcs12Convertor.parsePrivateKey(reader)
      keyPair.getPrivate.getEncoded must_== pk.getEncoded
    }

    "parseCertificate" in {
      val keyPair = RSAKeyPairFactory.generate
      val builder = new X500NameBuilder()
      builder.addRDN(BCStyle.C, "JP")
      builder.addRDN(BCStyle.CN, "Pigumer Group")
      val csr = CertificationSigningRequestFactory.generate(builder.build(), keyPair)

      val now = Instant.now()
      val notBefore = new Date(now.toEpochMilli)
      val notAfter = new Date(now.plus(Duration.ofDays(365)).toEpochMilli)

      val b = new X509v3CertificateBuilder(csr.getSubject, BigInteger.valueOf(1L), notBefore, notAfter, csr.getSubject, csr.getSubjectPublicKeyInfo)

      val sigAlgId = new AlgorithmIdentifier(OIWObjectIdentifiers.sha1WithRSA)
      val digAlgId = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1)

      val privateKeyInfo = PrivateKeyFactory.createKey(keyPair.getPrivate.getEncoded)
      val contentSigner = new BcRSAContentSignerBuilder(sigAlgId, digAlgId).build(privateKeyInfo)
      val certificate = new JcaX509CertificateConverter().getCertificate(b.build(contentSigner))

      val os = new FileOutputStream("test.p12")
      try {
        Pkcs12Convertor.write(os, keyPair.getPrivate, "test".toCharArray, certificate)
      } finally {
        os.close
      }

      success
    }
  }
}
