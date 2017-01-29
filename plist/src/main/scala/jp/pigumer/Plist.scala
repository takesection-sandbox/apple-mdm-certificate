package jp.pigumer

import java.security.{PrivateKey, Signature}
import java.util.Base64

import org.bouncycastle.pkcs.PKCS10CertificationRequest

case class Plist(csr: PKCS10CertificationRequest, chain: String, privateKey: PrivateKey) {

  def toXml = {
    val csrString = Base64.getEncoder.encodeToString(csr.getEncoded)
    val signature = Signature.getInstance("SHA1WithRSA")
    signature.initSign(privateKey)
    signature.update(csr.getEncoded)
    val signatureString = Base64.getEncoder.encodeToString(signature.sign)

    s"""<?xml version="1.0" encoding="UTF-8"?>
       |<!DOCTYPE plist PUBLIC "-//Apple Inc//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
       |<plist version="1.0">
       |<dict>
       |<key>PushCertRequestCSR</key>
       |<string>
       |$csrString
       |</string>
       |<key>PushCertCertificateChain</key>
       |<string>
       |$chain
       |</string>
       |<key>PushCertSignature</key>
       |<string>
       |$signatureString
       |</string>
       |</dict>
       |</plist>
     """.stripMargin
  }

}