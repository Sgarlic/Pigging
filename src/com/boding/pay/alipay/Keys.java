﻿/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.boding.pay.alipay;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	//合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088111040882243";

	//收款支付宝账号
	//public static final String DEFAULT_SELLER = "541490579@qq.com";
	public static final String DEFAULT_SELLER = "331072367@qq.com";
	//商户私钥，自助生成
	//public static final String PRIVATE = "MIICXgIBAAKBgQDTAMNC7UH7+jPYI8Y2olyxm872Mmh/s9WDGC/HQX8BoR3z60Skwdffy+dnQeuDsX8KmS0TMKl0cszQGVCWMZTu9BjRViG7mdDnBf9RYB7OkC476rBwiWYhVTgdenin3gsyDPF78PjWcI3fJ1OqJ2zCERna6D+zH5y2+sMHpcShgQIDAQABAoGBAKuazzM7KpL35fo+/hGo8qIwCZ4JWRui8l7v6Muw5M46btXV3bnxPNRyTPyZjCVK0fuYoCQfS3G9e/0y2WuNX4WNSxeDFVjfPnFprOApwR9Zs5hxE6DvRVpzGGkKIuUFk2OqSlU5knW3hixY0ze1cFQWLFdUaVqxGUJnIA1WDTcRAkEA6eue+tWRdfVmav4HpcoMBnvuh6aivGyj8UaQPsgsZZRsNRuNVLU5R7m4APoPsuJ9jlfedMwD9jy6vVXcUrueuwJBAObrX1wjgyCTUdfH5JVNqlYeCG6G2F9g15awHW0fSDEDNOZP2pKfBy5X1Shjecqq2AcbcwEx3kH3b5swPukWgvMCQQCzYMv47e0t15FCWqrip52xGzg/Lbl9ZJITY1kZy8a/3qFb2FT4sD/MkM5ZcWi4dJZzoU4loo0nNkq9Bip+BwIhAkEAwXCi7ZiCHCPNB6nX/oqVCpcWESC82QlTEFRwEh2GaCh7hAOY4gJ+DYBee/r456G+XhXMfU+9FB2nTKVZzgChqQJANihuezTFCEPLMcnHvXBGe6KBFGePjZ0VMHBDOzimkk4olq6JSrRLwbipZUrUdFYAHp2e5mm30MqNzAh55hlqVA==";
	public static final String PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANMAw0LtQfv6M9gjxjaiXLGbzvYyaH+z1YMYL8dBfwGhHfPrRKTB19/L52dB64OxfwqZLRMwqXRyzNAZUJYxlO70GNFWIbuZ0OcF/1FgHs6QLjvqsHCJZiFVOB16eKfeCzIM8Xvw+NZwjd8nU6onbMIRGdroP7MfnLb6wwelxKGBAgMBAAECgYEAq5rPMzsqkvfl+j7+EajyojAJnglZG6LyXu/oy7Dkzjpu1dXdufE81HJM/JmMJUrR+5igJB9Lcb17/TLZa41fhY1LF4MVWN8+cWms4CnBH1mzmHEToO9FWnMYaQoi5QWTY6pKVTmSdbeGLFjTN7VwVBYsV1RpWrEZQmcgDVYNNxECQQDp65761ZF19WZq/gelygwGe+6HpqK8bKPxRpA+yCxllGw1G41UtTlHubgA+g+y4n2OV950zAP2PLq9VdxSu567AkEA5utfXCODIJNR18fklU2qVh4IbobYX2DXlrAdbR9IMQM05k/akp8HLlfVKGN5yqrYBxtzATHeQfdvmzA+6RaC8wJBALNgy/jt7S3XkUJaquKnnbEbOD8tuX1kkhNjWRnLxr/eoVvYVPiwP8yQzllxaLh0lnOhTiWijSc2Sr0GKn4HAiECQQDBcKLtmIIcI80Hqdf+ipUKlxYRILzZCVMQVHASHYZoKHuEA5jiAn4NgF57+vjnob5eFcx9T70UHadMpVnOAKGpAkA2KG57NMUIQ8sxyce9cEZ7ooEUZ4+NnRUwcEM7OKaSTiiWrolKtEvBuKllStR0VgAenZ7mabfQyo3MCHnmGWpU";
	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}