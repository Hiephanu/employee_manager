package com.example.ncc_spring.service.auth;

import com.example.ncc_spring.enums.Provider;
import com.example.ncc_spring.enums.Role;
import com.example.ncc_spring.exception.ExceptionEntity.BadRequestException;
import com.example.ncc_spring.exception.ExceptionEntity.InternalServerErrorException;
import com.example.ncc_spring.exception.ExceptionEntity.NotFoundException;
import com.example.ncc_spring.exception.ExceptionEntity.UnauthorizedException;
import com.example.ncc_spring.model.dto.EmployeeResDto;
import com.example.ncc_spring.model.dto.JwtEncryptData;
import com.example.ncc_spring.model.dto.LoginRequestDto;
import com.example.ncc_spring.model.dto.auth.LoginResDto;
import com.example.ncc_spring.model.entity.AccountGoogle;
import com.example.ncc_spring.model.entity.AccountUsername;
import com.example.ncc_spring.repository.auth.AccountGoogleRepository;
import com.example.ncc_spring.repository.auth.AccountUsernameRepository;
import com.example.ncc_spring.service.employee.EmployeeCrudService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LoginService {
    private final AccountUsernameRepository accountUsernameRepository;
    private final AccountGoogleRepository accountGoogleRepository;
    private final AccountService accountService;
    private final EmployeeCrudService employeeCrudService;
    private final JwtService jwtService;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;

    public LoginResDto login(LoginRequestDto loginRequestDto) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getUsername(), loginRequestDto.getPassword()
            ));
            AccountUsername account = accountUsernameRepository.findByUsername(loginRequestDto.getUsername());
            if(account == null) {
                throw new NotFoundException("User isn't found");
            }
            EmployeeResDto employeeResDto = employeeCrudService.getEmployeeById(account.getA_idEmployee());
            if(bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), account.getPassword())) {
                JwtEncryptData jwtEncryptData = JwtEncryptData.builder()
                        .accountId(account.getA_id())
                        .employeeId(account.getA_idEmployee())
                        .role(account.getA_role())
                        .build();
                return LoginResDto.builder()
                        .token(jwtService.genToken(jwtEncryptData))
                        .refreshToken(jwtService.genRefreshToken(jwtEncryptData))
                        .employeeId(employeeResDto.getId())
                        .employee_name(employeeResDto.getName())
                        .employee_photo("https://cellphones.com.vn/sforum/wp-content/uploads/2024/02/avatar-anh-meo-cute-1.jpg")
                        .build();
            } else {
                throw  new UnauthorizedException("Password is invalid");
            }
    }

    public LoginResDto loginWithGoogle(String googleToken) {
        try {
            NetHttpTransport transport = new NetHttpTransport();
            JsonFactory jsonFactory = new GsonFactory();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .build();
            GoogleIdToken googleIdToken = verifyTokenId(verifier, googleToken);

            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            String email = payload.getEmail();
            System.out.println(email);
            EmployeeResDto employeeResDto = employeeCrudService.getEmployeeByEmail(email);

            AccountGoogle accountGoogle = accountService.getAccountByEmail(email);

            if (accountGoogle == null) {
                AccountGoogle accountGoogleSave = new AccountGoogle();
                accountGoogleSave.setEmail(email);
                accountGoogleSave.setA_idEmployee(employeeResDto.getId());
                accountGoogleSave.setA_provider(Provider.GOOGLE);
                accountGoogleSave.setA_role(Role.EMPLOYEE);

                AccountGoogle accountGoogleSaveRes = this.accountGoogleRepository.save(accountGoogleSave);
                return LoginResDto.builder().token(jwtService.genToken(JwtEncryptData.builder()
                        .accountId(accountGoogleSaveRes.getA_id())
                        .employeeId(accountGoogleSaveRes.getA_idEmployee())
                        .role(accountGoogleSaveRes.getA_role())
                        .build())).build();

            } else {
                JwtEncryptData jwtEncryptData =  JwtEncryptData.builder()
                        .accountId(accountGoogle.getA_id())
                        .employeeId(accountGoogle.getA_idEmployee())
                        .role(accountGoogle.getA_role()).build();
                return LoginResDto.builder().token(jwtService.genToken(jwtEncryptData))
                        .refreshToken(jwtService.genRefreshToken(jwtEncryptData))
                        .accountId(accountGoogle.getA_id())
                        .employeeId(accountGoogle.getA_idEmployee())
                        .build();

            }
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad_request");
        } catch (UnauthorizedException e) {
                throw new UnauthorizedException("unauthorized");
        } catch (InternalServerErrorException e) {
            throw  new InternalServerErrorException("Internal error");
        }
    }

    private GoogleIdToken verifyTokenId(GoogleIdTokenVerifier verifier, String ggIdTokenString) {
        try {
            final GoogleIdToken idToken = verifier.verify(ggIdTokenString);
            if(idToken == null){
                throw new BadRequestException("Token can't be null");
            } else {
                return idToken;
            }
        } catch (Exception e) {
            throw new UnauthorizedException("unauthorized");
        }
    }
}
