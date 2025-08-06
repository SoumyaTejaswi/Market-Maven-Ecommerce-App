package com.marketmaven.MarketMaven.Ecommerce.service.impl;


import com.marketmaven.MarketMaven.Ecommerce.dto.AddressDto;
import com.marketmaven.MarketMaven.Ecommerce.dto.Response;
import com.marketmaven.MarketMaven.Ecommerce.entity.Address;
import com.marketmaven.MarketMaven.Ecommerce.entity.User;
import com.marketmaven.MarketMaven.Ecommerce.repository.AddressRepo;
import com.marketmaven.MarketMaven.Ecommerce.service.interf.AddressService;
import com.marketmaven.MarketMaven.Ecommerce.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //injecting dependable
public class AddressServiceImpl implements AddressService {
    private final AddressRepo addressRepo;//Needs AddressRepo for database operations
    private final UserService userService;//Uses UserService for user-related operations

    @Override
    public Response saveAndUpdateAddress(AddressDto addressDto)//Takes an AddressDto containing address details
    {
        //User to update his address
        User user=userService.getLoggedInUser();//Retrieves logged-in user
        Address address=user.getAddress();//Gets user's existing address from DB

        if(address==null){
            address=new Address();//Creates new address if none exists
            address.setUser(user);//Associates address with user
        }
        //updates only provided fields
        if(addressDto.getStreet()!=null) address.setStreet(addressDto.getStreet());
        if(addressDto.getCity()!=null) address.setCity(addressDto.getCity());
        if(addressDto.getState()!=null) address.setState(addressDto.getState());
        if(addressDto.getZip()!=null) address.setZip(addressDto.getZip());
        if(addressDto.getCountry()!=null) address.setCountry(addressDto.getCountry());

        addressRepo.save(address);//Saves address to database
        String message=(user.getAddress()==null?"Address Successfully Created":"Address Updated Successfully");
        //Returns appropriate success message

        return Response.builder()
                .status(200)
                .message(message)
                .build();
        //builds the response
    }
}
