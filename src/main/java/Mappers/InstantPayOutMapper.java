package Mappers;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.Ipaisa.Entitys.InstantPayOut;
import com.Ipaisa.Entitys.User;

@Mapper
public interface InstantPayOutMapper {
	 InstantPayOutMapper INSTANCE = Mappers.getMapper(InstantPayOutMapper.class);

//	 @Mapping(target = "user", source = "user")
//	    @Mapping(target = "statuscode", source = "jsonMap.statuscode")
//	    @Mapping(target = "actcode", source = "jsonMap.actcode")
//	    @Mapping(target = "status", source = "jsonMap.status")
//	    @Mapping(target = "externalRef", source = "data.externalRef")
//	    @Mapping(target = "poolReferenceId", source = "data.poolReferenceId")
//	    @Mapping(target = "txnValue", source = "data.txnValue")
//	    @Mapping(target = "txnReferenceId", source = "data.txnReferenceId")
//	    @Mapping(target = "poolAccount", source = "data.pool.account")
//	    @Mapping(target = "poolOpeningBal", source = "pool.openingBal")
//	    @Mapping(target = "poolMode", source = "data.pool.mode")
//	    @Mapping(target = "poolAmount", source = "data.pool.amount")
//	    @Mapping(target = "poolClosingBal", source = "data.pool.closingBal")
//	    @Mapping(target = "payerAccount", source = "data.payer.account")
//	    @Mapping(target = "payerName", source = "data.payer.name")
//	    @Mapping(target = "payeeAccount", source = "data.payee.account")
//	    @Mapping(target = "payeeName", source = "data.payee.name")
	 
	    InstantPayOut mapToInstantPayOut(Map<String, Object> jsonMap, Map<String, Object> data, User user);
	 default Map<String, Object> getNestedMap(Map<String, Object> data, String key) {
	        return (Map<String, Object>) data.get(key);
	    }
	}