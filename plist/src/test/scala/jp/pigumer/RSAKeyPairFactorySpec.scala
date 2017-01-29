package jp.pigumer

import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class RSAKeyPairFactorySpec extends Specification {

  "RSAKeyPairFactory" should {

    "PrivateKeyToPEM" in new Scope {
      val keyPair = RSAKeyPairFactory.generate
      val pem = RSAKeyPairFactory.privateKeyToString(keyPair)
      pem.startsWith("-----BEGIN RSA PRIVATE KEY-----") must beTrue
    }
  }

}
