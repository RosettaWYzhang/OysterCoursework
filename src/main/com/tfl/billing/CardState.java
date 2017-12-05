package com.tfl.billing;


import java.util.UUID;

public interface CardState {
    void cardScanned(UUID cardId);
}
