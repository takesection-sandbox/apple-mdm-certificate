package jp.pigumer

import org.bouncycastle.asn1.x500.X500NameBuilder
import org.bouncycastle.asn1.x500.style.BCStyle
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class CertificationSigningRequestFactorySpec extends Specification {

  "CertificationSigningRequestFactory" should {

    "CertificationSigningRequestToPEM" in new Scope {
      val subjectBuilder = new X500NameBuilder
      subjectBuilder.addRDN(BCStyle.C, "JP")
      subjectBuilder.addRDN(BCStyle.CN, "Pigumer Group")
      val subject = subjectBuilder.build

      val keyPair = RSAKeyPairFactory.generate

      val csr = CertificationSigningRequestFactory.generate(subject, keyPair)
      val pem = CertificationSigningRequestFactory.csrToString(csr)
      pem.startsWith("-----BEGIN CERTIFICATE REQUEST-----") must beTrue
    }
  }

}
