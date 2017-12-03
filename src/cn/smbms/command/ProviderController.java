package cn.smbms.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.smbms.helper.PageHelper;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.provider.service.ProviderService;
@Controller
@RequestMapping("/provider")
public class ProviderController {
	@Autowired
	private ProviderService service;
	@RequestMapping(value="/providerlist",method=RequestMethod.GET)
	public String index(){
		return "/providerlist";
	}
	@RequestMapping(value="/providerlist",method=RequestMethod.POST)
	public ModelAndView queryProvider(PageHelper<Provider> helper){
		ModelAndView mv =  new ModelAndView();
		service.queryPaged(helper);
		helper.setTotalSize(helper.getTotalPage());
		mv.addObject("helper", helper);
		mv.setViewName("providerlist"); 
		return mv;
	}
	
	@RequestMapping(value="/proview/{proid}/{opr}",method=RequestMethod.GET)
	public ModelAndView userView(@PathVariable int proid,@PathVariable String opr,
			HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
		if(request.getSession().getServletContext().getAttribute("operator")==null)
			return new ModelAndView("redirect:/user/login");
		Provider provider = service.getProById(String.valueOf(proid));
		//扩大作用域。
		request.getSession().setAttribute("provider", provider);
		String jump = "";
		if(opr.equals("query"))
			jump="providerview";
		else if(opr.equals("update"))
			jump="redirect:/provider/providerUpdate";
		else
			jump="redirect:/provider/providerlist";
		return new ModelAndView(jump);
	}
	//通过重定向消除地址栏的参数信息以便使mvc准确查找到需要post请求的处理器。
	@RequestMapping(value="/providerUpdate",method=RequestMethod.GET)
	public ModelAndView userUpdate(){
		return new ModelAndView("providermodify");
	}
	
	@RequestMapping(value="/providerModify",method=RequestMethod.POST)
	public ModelAndView providerModify(Provider provider,HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
		if(request.getSession().getServletContext().getAttribute("operator")==null)
			return new ModelAndView("redirect:/user/login");
		String objString = "redirect:/provider/providerUpdate";
		boolean result = service.Modify(provider);
		if(result==true){
			objString = "redirect:/provider/providerlist";
		}
		return new ModelAndView(objString);
	}
	
	@RequestMapping(value="/provideradd",method=RequestMethod.GET)
	public String proAddMsg(){
		return "provideradd";
	}
	@RequestMapping(value="/provideradd",method=RequestMethod.POST)
	public String proAdd(@ModelAttribute Provider provider){
		String jump = "redirect:/provider/providerlist";
			if(service.add(provider)==false)
			   jump = "provideradd";
		return jump;
	}
	
	@RequestMapping(value="/billlist",method=RequestMethod.GET)
	public String indexB(){
		return "redirect:/bill/billlist";
	}
	@RequestMapping(value="/userlist",method=RequestMethod.GET)
	public String indexU(){
		return "redirect:/user/userlist";
	}
	@RequestMapping(value="/cLogin",method=RequestMethod.GET)
	public String cancleLogin(){
		return "redirect:/user/login";
	}
	@RequestMapping(value="/pwdModifiy",method=RequestMethod.GET)
	public String pwdModifiy(){
		return "redirect:/user/pwdModifiy";
	}
}
