package jp.pigumer.mdm.darwin.certificate

import java.io.{OutputStream, Reader}
import java.security.cert.X509Certificate
import java.security.{KeyStore, PrivateKey}

import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import org.bouncycastle.openssl.{PEMKeyPair, PEMParser}

object Pkcs12Convertor {

  def parsePrivateKey(reader: Reader): PrivateKey = {
    val pemParser = new PEMParser(reader)
    val pemKeyPair = pemParser.readObject().asInstanceOf[PEMKeyPair]
    new JcaPEMKeyConverter().getKeyPair(pemKeyPair).getPrivate
  }

  def parseCertificate(reader: Reader): X509Certificate = {
    val pemParser = new PEMParser(reader)
    pemParser.readObject().asInstanceOf[X509Certificate]
  }

  def write(os: OutputStream, privateKey: PrivateKey, password: Array[Char], certificate: X509Certificate): Unit = {
    val keyStore = KeyStore.getInstance("pkcs12")
    keyStore.load(null, password)

    keyStore.setKeyEntry("1", privateKey, password, Seq(certificate).toArray)
    keyStore.store(os, password)
  }

}
