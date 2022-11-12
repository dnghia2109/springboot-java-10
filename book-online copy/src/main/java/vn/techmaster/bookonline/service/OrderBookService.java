package vn.techmaster.bookonline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.techmaster.bookonline.repository.OrderBookRepository;

@Service
public class OrderBookService {
    @Autowired
    private OrderBookRepository orderBookRepository;



}
