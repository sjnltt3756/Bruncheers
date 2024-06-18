package com.bruncheers.chat.entity;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bruncheers.chat.dto.ChatDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@DynamicUpdate
@DynamicInsert
@Entity*/
public class Chat {

	/*	@Id
		@SequenceGenerator(name = "chat_chat_no_seq", initialValue = 1, allocationSize = 1)
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_chat_no_seq")
		private Long chNo;
	
		private String chName;
	
		private String chPhone;
	
		private String chContent;
		
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "ChatDto [chNo=" + chNo + ", chName=" + chName + ", chPhone=" + chPhone + ", chContent=" + chContent
					+ "]\n";
		}
	
		public static Chat toEntity(ChatDto chatDto) {
	
			return Chat.builder().chNo(chatDto.getChNo()).chName(chatDto.getChName()).chPhone(chatDto.getChPhone())
					.chContent(chatDto.getChContent()).build();
		}*/

}
