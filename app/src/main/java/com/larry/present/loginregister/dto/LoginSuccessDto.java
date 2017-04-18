package com.larry.present.loginregister.dto;

/*  
*    
* 项目名称：present-android      
* 类描述： 登录成功的返回dto
* 创建人：Larry-sea   
* 创建时间：2017/4/18 22:01   
* 修改人：Larry-sea  
* 修改时间：2017/4/18 22:01   
* 修改备注：   
* @version    
*    
*/
public class LoginSuccessDto {

    //用户登录以后返回的token
    String token;
    //用户类型  1 代表学生用户给  2  代表老师用户
    int userType;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
