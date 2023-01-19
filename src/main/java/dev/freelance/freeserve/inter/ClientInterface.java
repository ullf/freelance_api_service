package dev.freelance.freeserve.inter;

import dev.freelance.freeserve.entity.AbstractClient;

public interface ClientInterface {
    boolean isFreelancer();
    boolean isBuyer();
    AbstractClient createAbstractClient(String name,String surname,boolean indicator);
    AbstractClient findAbstractClientByNickname(String nickname);
}