package jp.pigumer

import java.io.StringWriter
import java.security.{KeyPair, KeyPairGenerator}

import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator
import org.bouncycastle.util.io.pem.PemWriter


object RSAKeyPairFactory {

  def generate: KeyPair = {
    val generator = KeyPairGenerator.getInstance("RSA")
    generator.initialize(2048)
    generator.genKeyPair()
  }

  def privateKeyToString(keyPair: KeyPair) = {
    val writer = new StringWriter
    try {
      val pemWriter = new PemWriter(writer)
      try {
        val pemObject = new JcaMiscPEMGenerator(keyPair.getPrivate)
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

  def publicKeyToString(keyPair: KeyPair) = {
    val writer = new StringWriter
    try {
      val pemWriter = new PemWriter(writer)
      try {
        val pemObject = new JcaMiscPEMGenerator(keyPair.getPublic)
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
