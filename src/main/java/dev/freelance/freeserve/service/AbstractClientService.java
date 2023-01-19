package dev.freelance.freeserve.service;

import dev.freelance.freeserve.entity.AbstractClient;
import dev.freelance.freeserve.entity.BuyerClient;
import dev.freelance.freeserve.inter.ClientInterface;
import dev.freelance.freeserve.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
This is a Java class called "AbstractClientService" that implements the "ClientInterface" interface.
It is annotated with "@Service" and "@AllArgsConstructor" which suggests that it is a service class and uses constructor injection.
The class has a single private field called "clientRepository" which is used to save "AbstractClient" objects.
The class has two methods "isFreelancer()" and "isBuyer()" which both return boolean values, and one method "createAbstractClient(AbstractClient abstractClient)"
which takes in an "AbstractClient" object as a parameter and saves it to the repository. If the "isIndicator" field of the "AbstractClient" object is false,
it saves the object as is, but if it is true, it modifies the "nickname" field by prefixing "frl_" to it before saving it. The method returns a "ResponseEntity"
object that contains the saved "AbstractClient" object.
 */
@Service
@AllArgsConstructor
public class AbstractClientService implements ClientInterface {

    private ClientRepository clientRepository;

    @Override
    public boolean isFreelancer() {
        return true;
    }

    @Override
    public boolean isBuyer() {
        return false;
    }
    /*
    The method "createAbstractClient" appears to be used for creating an "AbstractClient" object and saving it to the repository.
    It checks the value of the "isIndicator" field of the "AbstractClient" object passed as a parameter.
    If the value is false, it saves the object as is, but if it is true, it modifies the "nickname" field by prefixing "frl_" to it before saving it.
    It returns a "ResponseEntity" object with the saved "AbstractClient" object as the body and HTTP status code 200 (OK) to indicate success.
    It is not clear what the purpose of the "isIndicator" field is or why it is necessary to modify the "nickname" field in this manner.
     */
    public ResponseEntity<?> createAbstractClient(AbstractClient abstractClient) {
        if (abstractClient.isIndicator() == false) {
            clientRepository.save(abstractClient);
            return ResponseEntity.ok(abstractClient);
        } else {
            abstractClient.setNickname("frl_"+abstractClient.getNickname());
            clientRepository.save(abstractClient);
            return ResponseEntity.ok(abstractClient);
        }
    }

    @Override
    public AbstractClient createAbstractClient(String name,String surname,boolean indicator) {
        if (indicator == false) {
            AbstractClient buyer = new BuyerClient();
            buyer.setIndicator(indicator);
            buyer.setName(name);
            buyer.setNickname(name);
            buyer.setSurname(surname);
            clientRepository.save(buyer);
            return buyer;
        } else {
            AbstractClient freelancer = new AbstractClient();
            freelancer.setIndicator(indicator);
            freelancer.setName(name);
            freelancer.setNickname("frl_"+name);
            freelancer.setSurname(surname);
            clientRepository.save(freelancer);
            return freelancer;
        }
    }

    public AbstractClient findAbstractClientByNickname(String nickname) {
        var client = clientRepository.findAbstractClientByNickname(nickname);
        return client;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientRepository.loadUserByUsername(username);
    }
}
