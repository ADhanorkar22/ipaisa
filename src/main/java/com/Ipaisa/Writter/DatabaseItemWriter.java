//package com.Ipaisa.Writter;
//import org.springframework.batch.item.Chunk;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import com.Ipaisa.Entitys.InstantPayBody;
//import com.Ipaisa.Repository.InstantPayBodyRepository;
//
//import java.util.List;
//
//@Component
//public class DatabaseItemWriter implements ItemWriter<InstantPayBody> {
//
//    
//
//    @Override 
//    public void write(List<? extends Object> items) throws Exception {
//        for (InstantPayBody item : items) {
//            // Example of using setters
//            item.setTransferAmount(item.getTransferAmount());
//          
//        }
//        repository.saveAll(items);
//    }
//
//	@Override
//	public void write(Chunk<? extends InstantPayBody> chunk) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//}