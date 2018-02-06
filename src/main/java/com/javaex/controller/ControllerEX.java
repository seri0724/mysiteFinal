package com.javaex.controller;

//예시
public class ControllerEX {
	
//	//맵핑하는 방법 3가지
//	//1)주소만 설정 :자동으로 get,post 둘다 사용
//	@RequestMapping("/list")
//	public String list() { //뱉어 내는 String 리턴형은 언제나 페이지 명
//		System.out.println("user/list 진입");
//		return "index";  //현재 자동적으로 DispatcherServlet 내부에서 forward 를 사용하는 상태이다
//	}
//	
//	//2)get,post 둘중 하나만 사용 : 값이 1개 이상 필요해지기 때문에 지정을 위해서 앞에 변수명 필요
//	@RequestMapping(value="/form" , method = RequestMethod.GET)
//	public String form() {
//		System.out.println("user/form 진입");
//		return "form";
//	}
//	
//	//3)1번과 차이가 없지만 자신이 직접 기입
//	@RequestMapping( value="/form2", method={RequestMethod.GET, RequestMethod.POST} )
//	public String form2() {
//		System.out.println("user/form2 진입");
//		return "form";
//	}
//	
//	
//	//request 는 DispatcherServlet이 가지고있고 컨트롤러가 필요하다고 매개변수안에 어노테이션으로 표기한 파라미터만 꺼내준다
//	//request로 받은 값 매개변수로 받아오기
//	//1)기본사용
//	@RequestMapping(value="/add" , method = RequestMethod.GET)
//	public String add( @RequestParam("age") int age , @RequestParam("name") String name) { 
//		System.out.println("add 진입");
//		System.out.println("나이 : " + age);
//		System.out.println("이름 : " + name);
//		return "index";
//	}
//	
//	//2)값을 2개 받아와야하는데 하나만 받게될때를 대비해서 디폴트값을 설정 하는법
//	@RequestMapping(value="/add2" , method = RequestMethod.GET)
//	public String add2( @RequestParam(value="age",required=false,defaultValue="120") int age , @RequestParam("name") String name) { //
//		System.out.println("add2 진입");
//		System.out.println("나이 : " + age);
//		System.out.println("이름 : " + name);
//		return "index";
//	}
//	
//	//3)vo를 만들어서 가져오는 방법
//	@RequestMapping(value="/add3" , method = RequestMethod.GET)
//	public String add3(@ModelAttribute UserVo uvo){ 
//		System.out.println("add3 진입");
//		System.out.println("나이 : " + uvo.getAge());
//		System.out.println("이름 : " + uvo.getAge());
//		return "index";
//	}
//	
//	//4)url에 직접 담겨오는 파라미터 사용
//	@RequestMapping(value="/view/{no}")
//	public String view(@PathVariable("no") int no) {
//		System.out.println("view 진입");
//		System.out.println(no);
//		return "index";
//	}
//	
//	//5)request,response,session 가져오기
//	@RequestMapping("sp")
//	public String sp(HttpServletRequest request , HttpServletResponse response, HttpSession session) {
//		System.out.println("view 진입");
//		return "index";
//	}
//	
//	//6)모델 사용하기
//	@RequestMapping("model")
//	public String model(Model model) {
//		String str = "model에서 보낸 문자열";
//		model.addAttribute("str", str); //객체형 자료를 모델에 담아 DispatcherServlet 에게 넘겨주면 ds 가 requset에 세팅한다.
//		return "index"; 
//	}
//	
//	
//	//redirect 사용하는 법~!
//	@RequestMapping("/user/join")
//	public String model( @ModelAttribute UserVo userVo) {
//		return "redirect:/user/joinsuccess"; 
//	}
}
