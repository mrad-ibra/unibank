package com.unibank.demo.redis.repository;

import com.unibank.demo.redis.entity.CurrencyRate;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRateRepository extends CrudRepository<CurrencyRate,String> {


}
