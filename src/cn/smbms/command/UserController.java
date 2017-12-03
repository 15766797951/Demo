package cn.smbms.command;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.smbms.helper.PageHelper;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.role.service.RoleService;
import cn.smbms.user.service.UserService;
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@ExceptionHandler(value={RuntimeException.class})
	public String HandlerException(RuntimeException e,HttpServletRequest request){
		request.setAttribute("e", e);
		return "error";
	}
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String indexInit(){
		return "/login";
	}
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView login(String userCode,String userPassword,
			HttpServletRequest request,HttpServletResponse response,Model model) throws UnsupportedEncodingException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String jump ="login";
		User user = userService.login(userCode, userPassword);
		if(user!=null){
			jump="userlist";
			//ServletContext application = request.getSession().getServletContext();
			//application.setAttribute("operator", user);
			request.getSession().setAttribute("operator", user);
			List<Role> roleList = roleService.getAllRole();
			request.getSession().setAttribute("roleList", roleList);
		}
		else{
			throw new RuntimeException("用户名或密码错误");
		}
		return new ModelAndView(jump);
	}
	
	@RequestMapping(value="/userlist",method=RequestMethod.GET)
	public ModelAndView userlist(@Param("queryname")String queryname,@Param("queryUserRole")Integer queryUserRole,
			HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
		if(request.getSession().getAttribute("operator")==null)
			return new ModelAndView("redirect:/user/login");
		List<User> userList = userService.getUserList(queryname, queryUserRole, 0, 0);
		request.getSession().setAttribute("queryUserRole",queryUserRole);
		model.addAttribute("queryUserName",queryname);
		model.addAttribute("userList", userList);
		
		return new ModelAndView("userlist");
	}
	
	@RequestMapping(value="/userview/{uid}/{opr}",method=RequestMethod.GET)
	public ModelAndView userView(@PathVariable int uid,@PathVariable String opr,
			HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
		if(request.getSession().getAttribute("operator")==null)
			return new ModelAndView("redirect:/login");
		User user = userService.getUserById(String.valueOf(uid));
		//扩大作用域。
		request.getSession().setAttribute("user", user);
		String jump = "";
		if(opr.equals("query"))
			jump="userview";
		else if(opr.equals("update"))
			jump="redirect:/user/userUpdate";
		else
			jump="redirect:/user/userlist";
		return new ModelAndView(jump);
	}
	//通过重定向消除地址栏的参数信息以便使mvc准确查找到需要post请求的处理器。
	@RequestMapping(value="/userUpdate",method=RequestMethod.GET)
	public ModelAndView userUpdate(){
		return new ModelAndView("usermodify");
	}
	@RequestMapping(value="/usermodify",method=RequestMethod.POST)
	public ModelAndView userModify(User user,HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
		if(request.getSession().getAttribute("operator")==null)
			return new ModelAndView("redirect:/user/login");
		String objString = "redirect:/user/userUpdate";
		boolean result = userService.modify(user);
		if(result==true){
			objString = "redirect:/user/userlist";
		}
		return new ModelAndView(objString);
	}

	/**
	 * 分页实现
	 */
	@RequestMapping("/query")
	public ModelAndView queryPaged(PageHelper<User> helper){
		helper.setPageNo(1);
		ModelAndView mv =  new ModelAndView();
		userService.queryPaged(helper);
		helper.setTotalSize(helper.getTotalPage());
		mv.addObject("helper", helper);
		mv.setViewName("userlist"); 
		return mv; 
	}
	@RequestMapping(value="/useradd",method=RequestMethod.GET)
	public String userAddMsg(){
		return "useradd";
	}
	@RequestMapping(value="/useradd",method=RequestMethod.POST)
	public String userAdd(@ModelAttribute User user){
		String jump = "redirect:/user/userlist";
			if(userService.add(user)==false)
			   jump = "useradd";
		return jump;
	}
	@RequestMapping(value="/checkUser",method=RequestMethod.GET,consumes="application/json")
	@ResponseBody
	public String checkUser(String method,String userCode) throws JsonProcessingException{
		Map<String, Object> map = new HashMap<String,Object>();
		String result = "ucexist";
		User user = userService.selectUserCodeExist(userCode);
		if(user!=null)
			result = "exist";
		map.put("userCode", result);
		ObjectMapper mapper = new ObjectMapper();
		userCode = mapper.writeValueAsString(map);
		return userCode;
	}
	
	@RequestMapping(value="/RoleMes",method=RequestMethod.GET,consumes="application/json")
	@ResponseBody
	public String RoleMes(String method) throws JsonProcessingException{
		JSONArray array = new JSONArray();
		method = null;
		List<Role> role = roleService.getAllRole();
		for (Role role2 : role) {
			JSONObject obj = new JSONObject();
			obj.put("roleId", role2.getId());
			obj.put("roleName", role2.getRoleName());
			array.add(obj);
		}
		return array.toString();
	}
	
	@RequestMapping(value="/providerlist",method=RequestMethod.GET)
	public String indexP(){
		return "redirect:/provider/providerlist";
	}
	@RequestMapping(value="/billlist",method=RequestMethod.GET)
	public String indexB(){
		return "redirect:/bill/billlist";
	}
	@RequestMapping(value="/cLogin",method=RequestMethod.GET)
	public String cancleLogin(){
		return "redirect:/user/login";
	}
	@RequestMapping(value="/pwdModifiy",method=RequestMethod.GET)
	public String pwdModifiy(){
		return "redirect:/user/pwdModifiys";
	}
	@RequestMapping(value="/pwdModifiys",method=RequestMethod.GET)
	public String pwdModifiys(){
		return "pwdmodify";
	}
	@RequestMapping(value="/pwdModifiyUpdate",method=RequestMethod.POST)
	public String pwdModifiyUpdate(String rnewpassword,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("operator");
		user.setUserPassword(rnewpassword);
		boolean result = userService.updatePwd(user);
		if(result)
			return "login";
		return "pwdmodify";
	}
	@RequestMapping(value="/pwdModifiyCheck",method=RequestMethod.GET,consumes="application/json")
	@ResponseBody
	public String pwdModifiyCheck(String method,String oldpassword,HttpServletRequest request) throws JsonProcessingException{
		User user = (User) request.getSession().getAttribute("operator");
		Map<String, Object> map = new HashMap<String,Object>();
		String result = "";
		if(user==null)
			result= "sessionerror";
		else if(oldpassword.equals(""))
			result= "error";
		else if(oldpassword.equals(user.getUserPassword()))
			result= "true";
		else 
			result= "false";
		map.put("result", result);
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(map);
	}
}
