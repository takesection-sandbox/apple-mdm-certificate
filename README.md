# 概要

* [APNs Overview](https://developer.apple.com/library/content/documentation/NetworkingInternet/Conceptual/RemoteNotificationsPG/APNSOverview.html#//apple_ref/doc/uid/TP40008194-CH8-SW1)
* [MDM Vendor CSR Signing Overview](https://developer.apple.com/library/content/documentation/Miscellaneous/Reference/MobileDeviceManagementProtocolRef/7-MDMVendorCSRSigningOverview/MDMVendorCSRSigningOverview.html#//apple_ref/doc/uid/TP40017387-CH6-SW4)

# MDM証明書要求の手順

## RSAの鍵ペアの生成

最初にRSAの公開鍵と秘密鍵のペアを生成します。
秘密鍵はプッシュ通信を行う時に使用するため、生成した秘密鍵は厳重に保管します。

## 証明書署名要求(Certificataion Signing Request)の生成

Apple社のMDM証明書のCSRは独自のフォーマット(plist)で生成する必要があります。

### 1. 証明書署名要求の生成

証明書署名要求をRSAの秘密鍵でSHA1を使って署名したバイナリイメージをBase64で変換した文字列を生成します。

### 2. 証明書チェーンの生成

以下のPEM形式の証明書ファイルを結合します。

* MDMベンダーの証明書
* [Apple社のWWDR中間証明書](http://developer.apple.com/certificationauthority/AppleWWDRCA.cer)
* [Apple社のルート証明書](http://www.apple.com/appleca/AppleIncRootCertificate.cer)

### 3. 証明書署名要求のバイナリを署名

証明書署名要求のバイナリをMDMベンダー証明書のRSA秘密鍵でSHA1を使って署名したバイナリイメージをBase64で変換した文字列を生成します。

### 前述の手順で作成したイメージを元に、plistイメージの生成

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple Inc//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
<key>PushCertRequestCSR</key>
<string>証明書署名要求(*1)</string>
<key>PushCertCertificateChain</key>
<string>証明書チェーン(*2)</string>
<key>PushCertSignature</key>
<string>署名した証明書署名要求(*3)</string>
</dict>
</plist>
```

### plistイメージをBase64でエンコード

plistイメージをBase64でエンコードしたイメージをファイルに出力して、[Apple Push Certificates Portal](https://identity.apple.com/pushcert)にアップロードします。

## Apple社からMDM証明書をダウンロードする

Apple社にアップロードした後、MDM証明書のダウンロードボタンが有効となります。
ダウンロードされるMDM証明書はPEM形式のテキストファイルです。
