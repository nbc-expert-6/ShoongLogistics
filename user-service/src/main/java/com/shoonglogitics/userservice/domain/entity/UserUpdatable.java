package com.shoonglogitics.userservice.domain.entity;

import com.shoonglogitics.userservice.domain.vo.Email;
import com.shoonglogitics.userservice.domain.vo.Name;
import com.shoonglogitics.userservice.domain.vo.PhoneNumber;
import com.shoonglogitics.userservice.domain.vo.SlackId;

public interface UserUpdatable {
	void updateUserInfo(Name name, Email email, SlackId slackId, PhoneNumber phoneNumber);
}
