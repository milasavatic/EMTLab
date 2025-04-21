package mk.ukim.finki.emt.lab.service.application;

import mk.ukim.finki.emt.lab.dto.LoginResponseDto;
import mk.ukim.finki.emt.lab.dto.create.CreateUserDto;
import mk.ukim.finki.emt.lab.dto.display.DisplayUserDto;
import mk.ukim.finki.emt.lab.dto.LoginUserDto;

import java.util.Optional;

public interface UserApplicationService {
    Optional<DisplayUserDto> register(CreateUserDto createUserDto);

    Optional<LoginResponseDto> login(LoginUserDto loginUserDto);

    Optional<DisplayUserDto> findByUsername(String username);
}
