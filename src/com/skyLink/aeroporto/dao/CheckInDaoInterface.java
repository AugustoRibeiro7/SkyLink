// src/com/skyLink/aeroporto/dao/CheckInDaoInterface.java
package com.skyLink.aeroporto.dao;

import com.skyLink.aeroporto.model.CheckIn;

public interface CheckInDaoInterface {
    boolean inserir(CheckIn checkIn);
    CheckIn buscarPorTicket(int ticketId);
    boolean jaFezCheckIn(int ticketId);
}