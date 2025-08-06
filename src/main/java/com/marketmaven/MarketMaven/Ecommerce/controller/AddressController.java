package com.marketmaven.MarketMaven.Ecommerce.controller;

//Manages user address-related operations
import com.marketmaven.MarketMaven.Ecommerce.dto.AddressDto;
import com.marketmaven.MarketMaven.Ecommerce.dto.Response;
import com.marketmaven.MarketMaven.Ecommerce.dto.UserDto;
import com.marketmaven.MarketMaven.Ecommerce.service.interf.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//Tells spring that this is the file that handles web requests
@RequestMapping("/address")// All URLs in this controller will start with "/address"
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;// Create link to AddressService to use its functions
    @PostMapping("/save")//Handles Post requests to save the address
    //Method that takes address data and saves it.
    //returns a response wrapped in HttP Format.
    public ResponseEntity<Response> saveAndUpdateAddress(@RequestBody AddressDto addressDto) {
        //calls service to save address and return success response.
        return ResponseEntity.ok().body(addressService.saveAndUpdateAddress(addressDto));
    }
}
//address service is called here and as the service is called the implementation method is also called.