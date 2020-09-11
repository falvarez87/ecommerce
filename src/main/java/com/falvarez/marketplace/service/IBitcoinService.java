package com.falvarez.marketplace.service;

import java.util.Map;

public interface IBitcoinService {

	Map<Boolean, String> SendCoin(String privKey, String value);
}
