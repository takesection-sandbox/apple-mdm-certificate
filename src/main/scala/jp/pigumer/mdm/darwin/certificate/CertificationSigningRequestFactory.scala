package jp.pigumer.mdm.darwin.certificate

import java.io.StringWriter
import java.security.KeyPair

import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers
import org.bouncycastle.asn1.x500.X500Name
import org.bouncycastle.asn1.x509.AlgorithmIdentifier
import org.bouncycastle.crypto.util.{PrivateKeyFactory, PublicKeyFactory, SubjectPublicKeyInfoFactory}
import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator
import org.bouncycastle.operator.bc.BcRSAContentSignerBuilder
import org.bouncycastle.pkcs.{PKCS10CertificationRequest, PKCS10CertificationRequestBuilder}
import org.bouncycastle.util.io.pem.PemWriter

trait CertificationSigningRequestFactory {

  def generate(subject: X500Name, keyPair: KeyPair): PKCS10CertificationRequest = {
    val publicKeyParam = PublicKeyFactory.createKey(keyPair.getPublic.getEncoded)
    val subjectPublicKeyInfo = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(publicKeyParam)

    val builder = new PKCS10CertificationRequestBuilder(subject, subjectPublicKeyInfo)

    val sigAlgId = new AlgorithmIdentifier(OIWObjectIdentifiers.sha1WithRSA)
    val digAlgId = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1)

    val privateKeyParam = PrivateKeyFactory.createKey(keyPair.getPrivate.getEncoded)

    val contentSigner = new BcRSAContentSignerBuilder(sigAlgId, digAlgId).build(privateKeyParam)
    builder.build(contentSigner)
  }

  def toPEMString(csr: PKCS10CertificationRequest): String = {
    val writer = new StringWriter
    try {
      val pemWriter = new PemWriter(writer)
      try {
        val pemObject = new JcaMiscPEMGenerator(csr)
        pemWriter.writeObject(pemObject)
      }
      finally {
        pemWriter.close
      }
      writer.toString
    }
    finally {
      writer.close
    }
  }

}
