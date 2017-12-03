package cn.smbms.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.smbms.bill.service.BillService;
import cn.smbms.helper.PageHelper;
import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.provider.service.ProviderService;

@Controller
@RequestMapping("/bill")
public class BillController {
	@Autowired
	private BillService billService;
	@Autowired
	private ProviderService proService;
	
	@RequestMapping(value="/billlist",method=RequestMethod.GET)
	public String index(HttpServletRequest request){
		List<Provider> providerList = proService.getAllPro();
		request.getSession().setAttribute("providerList",providerList);
		return "/billlists";
	}
	@RequestMapping(value="/billlist",method=RequestMethod.POST)
	public ModelAndView billList(PageHelper<Bill> helper){
		ModelAndView mv =  new ModelAndView();
		billService.queryPaged(helper);
		helper.setTotalSize(helper.getTotalPage());
		mv.addObject("helper", helper);
		mv.setViewName("billlists"); 
		return mv;
	}
	
	@RequestMapping(value="/billView/{billid}/{opr}",method=RequestMethod.GET)
	public ModelAndView View(@PathVariable int billid,@PathVariable String opr,
			HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
		if(request.getSession().getServletContext().getAttribute("operator")==null)
			return new ModelAndView("redirect:/user/login");
		Bill bill = billService.getBillById(String.valueOf(billid));
		//扩大作用域。
		request.getSession().setAttribute("bill", bill);
		String jump = "";
		if(opr.equals("query"))
			jump="billview";
		else if(opr.equals("update"))
			jump="redirect:/bill/billUpdate";
		else
			jump="redirect:/bill/billlists";
		return new ModelAndView(jump);
	}
	//通过重定向消除地址栏的参数信息以便使mvc准确查找到需要post请求的处理器。
	@RequestMapping(value="/billUpdate",method=RequestMethod.GET)
	public ModelAndView Update(){
		return new ModelAndView("billmodify");
	}
	
	@RequestMapping(value="/billModify",method=RequestMethod.POST)
	public ModelAndView Modify(Bill bill,HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
		if(request.getSession().getServletContext().getAttribute("operator")==null)
			return new ModelAndView("redirect:/user/login");
		String objString = "redirect:/bill/billUpdate";
		boolean result = billService.Modify(bill);
		if(result==true){
			objString = "redirect:/bill/billlist";
		}
		return new ModelAndView(objString);
	}
	
	@RequestMapping(value="/billadd",method=RequestMethod.GET)
	public String AddMsg(){
		return "billadd";
	}
	@RequestMapping(value="/billadd",method=RequestMethod.POST)
	public String Add(@ModelAttribute Bill bill){
		String jump = "redirect:/bill/billlist";
			if(billService.add(bill)==false)
			   jump = "billadd";
		return jump;
	}
	@RequestMapping(value="/getALLpro",method=RequestMethod.GET,consumes="application/json")
	@ResponseBody
	public String getAllPro(String method){
		List<Provider> providerList = proService.getAllPro();
		JSONArray array = new JSONArray();
		for (Provider pro : providerList) {
			JSONObject object = new JSONObject();
			object.put("proId", pro.getId());
			object.put("proName", pro.getProName());
			array.add(object);
		}
		return array.toString();
	}
	@RequestMapping(value="/providerlist",method=RequestMethod.GET)
	public String indexP(){
		return "redirect:/provider/providerlist";
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
