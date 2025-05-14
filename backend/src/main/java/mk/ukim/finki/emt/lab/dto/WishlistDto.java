package mk.ukim.finki.emt.lab.dto;

import mk.ukim.finki.emt.lab.dto.display.DisplayBookDto;
import mk.ukim.finki.emt.lab.dto.display.DisplayUserDto;
import mk.ukim.finki.emt.lab.model.domain.Wishlist;

import java.util.List;

public record WishlistDto(Long id, DisplayUserDto user, List<DisplayBookDto> books) {
    public static WishlistDto from(Wishlist wishlist) {
        return new WishlistDto(
                wishlist.getId(),
                DisplayUserDto.from(wishlist.getUser()),
                DisplayBookDto.from(wishlist.getBooks())
        );
    }

}
