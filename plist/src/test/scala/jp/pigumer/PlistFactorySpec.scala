package jp.pigumer

import org.bouncycastle.asn1.x500.X500NameBuilder
import org.bouncycastle.asn1.x500.style.BCStyle
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class PlistFactorySpec extends Specification {

  "PlistFactory" should {

    "plist" in new Scope {
      val customKeyPair = RSAKeyPairFactory.generate
      val builder = new X500NameBuilder
      builder.addRDN(BCStyle.C, "JP")
      builder.addRDN(BCStyle.CN, "Pigumer Group")
      val csr = CertificationSigningRequestFactory.generate(builder.build, customKeyPair)

      val vendorKeyPair = RSAKeyPairFactory.generate
      val plist = PlistFactory.generatePlist(csr, "dummy", vendorKeyPair.getPrivate)
      plist.startsWith("<?") must beTrue
    }
  }
}
