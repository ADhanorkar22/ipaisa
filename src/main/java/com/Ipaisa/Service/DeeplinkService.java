package com.Ipaisa.Service;

import com.Ipaisa.dto.DeeplinkResponseDTO;
import com.Ipaisa.entity.DeeplinkRequest;
import com.Ipaisa.dto.DeeplinkResponseDTO;
import com.Ipaisa.entity.DeeplinkRequest;

public interface DeeplinkService {
 DeeplinkResponseDTO generateDeeplink(DeeplinkRequest request);
}
