package com.marketmaven.MarketMaven.Ecommerce.service.interf;

import com.marketmaven.MarketMaven.Ecommerce.dto.AddressDto;
import com.marketmaven.MarketMaven.Ecommerce.dto.Response;
import com.marketmaven.MarketMaven.Ecommerce.entity.Address;

public interface AddressService {
    Response saveAndUpdateAddress(AddressDto addressDto);
}
