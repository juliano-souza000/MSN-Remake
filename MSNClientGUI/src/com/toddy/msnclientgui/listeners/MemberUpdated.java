package com.toddy.msnclientgui.listeners;

import org.json.JSONObject;

public interface MemberUpdated
{
    void UpdateNewMember(JSONObject profile);
    void UpdateStatus(JSONObject profile);
    void UpdateAbout(JSONObject profile);
    void UpdateProfilePicture(JSONObject picture);
    void UpdateUsername(JSONObject username);
    void UpdateDeletedMember(int ID);
}