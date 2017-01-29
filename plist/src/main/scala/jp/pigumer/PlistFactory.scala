package jp.pigumer

import java.security.PrivateKey
import java.util.Base64

import org.bouncycastle.pkcs.PKCS10CertificationRequest

object PlistFactory {

  def generate(csr: PKCS10CertificationRequest,
                    vendorCertificate: String,
                    appleCertificate: String,
                    appleRootCertificate: String,
                    vendorPrivateKey: PrivateKey) = {
    val plist = generatePlist(csr, vendorCertificate + "\n" + appleCertificate + "\n" + appleRootCertificate, vendorPrivateKey)
    Base64.getEncoder.encodeToString(plist.getBytes())
  }

  def generatePlist(csr: PKCS10CertificationRequest,
                    chain: String,
                    vendorPrivateKey: PrivateKey) = {
    val plist = Plist(csr, chain, vendorPrivateKey)
    plist.toXml
  }

}
