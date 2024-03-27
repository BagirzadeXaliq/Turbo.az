package com.example.turboaz.mapper;

import com.example.turboaz.dao.entity.TransactionEntity;
import com.example.turboaz.model.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper {

    TransactionEntity mapToEntity(TransactionDTO transactionDTO);

    TransactionDTO mapToDTO(TransactionEntity transactionEntity);

}