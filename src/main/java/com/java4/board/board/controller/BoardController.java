package com.java4.board.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.java4.board.board.domain.Board;
import com.java4.board.board.service.BoardService;
import com.java4.board.comment.service.CommentService;
import com.java4.board.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {
	@Autowired
	BoardService boardService;
	@Autowired
	UserService userService;
	@Autowired
	CommentService commentService;
	
	@GetMapping("/")
	public String boardMainPage(@RequestParam(defaultValue = "1") int page, Model model) {
		model.addAttribute("title", "게시판");
		model.addAttribute("path", "/board/index"); 
		model.addAttribute("content", "boardFragment"); 
		model.addAttribute("contentHead", "boardFragmentHead");
		
		List<Board> list = boardService.getAll();		
		model.addAttribute("listSize",list.size());
		final int ITEMNUM = 10;
		model.addAttribute("pageSize",ITEMNUM);
		int index = (page-1) * ITEMNUM;
		List<Board> pagelist = boardService.getPage(index, ITEMNUM);
		model.addAttribute("list",pagelist);
		
		model.addAttribute("pageCount",4);		
		
		return "/basic/layout";
	}
	
	@PostMapping("/add")
	public String addItem(@RequestParam Map<String, String> data, HttpSession session, Model model) {
		try {
			if (session.getAttribute("userId")!=null) {
				String tempContent = data.get("content");
				tempContent = tempContent.replaceAll("width=\"[0-9]*\"", "width=\"100%\"");
				tempContent = tempContent.replaceAll("height=\"[0-9]*\"", "height=\"auto\"");
				int userIntId = userService.get((String) session.getAttribute("userId")).getId();
				boardService.add(new Board(data.get("title"),tempContent,userIntId));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("requestError","글 작성 실패");
		}
		return "redirect:/";
	}
	
	@GetMapping("/notice")
	public String noticePage(Model model) {
		model.addAttribute("title", "공지사항");
		model.addAttribute("path", "/board/notice"); 
		model.addAttribute("content", "noticeFragment"); 
		model.addAttribute("contentHead", "noticeFragmentHead");
		return "/basic/layout";
	}
	
//	@GetMapping("/board/item")
//	public String itemPage(@RequestParam("num") int number, Model model) {
//		model.addAttribute("title", "게시글");
//		model.addAttribute("path", "/board/item"); 
//		model.addAttribute("content", "itemFragment"); 
//		model.addAttribute("contentHead", "itemFragmentHead");	
//		
//		model.addAttribute("board", boardService.get(number));
//		model.addAttribute("boardContent", boardService.get(number).getContent().replace("\n","<br>"));
//		return "/basic/layout";
//	}
	
	@GetMapping("/board/{boardId}")
	public String boardPage(@PathVariable("boardId") int boardId, Model model) {
		model.addAttribute("title", "게시글");
		model.addAttribute("path", "/board/item"); 
		model.addAttribute("content", "itemFragment"); 
		model.addAttribute("contentHead", "itemFragmentHead");
		
		model.addAttribute("board", boardService.get(boardId));
		model.addAttribute("boardContent", boardService.get(boardId).getContent().replace("\n","<br>"));
		
		boardService.views(boardId);
		
//		model.addAttribute("comments", commentService.getComments(boardId));
		return "/basic/layout";
	}
	
	@PostMapping("/board/del")
	public String itemDelete(@RequestParam Map<String, String> data, Model model) {
		System.out.println(data.get("del-id"));
		int id = Integer.parseInt(data.get("del-id"));
		boardService.del(1, id);		
		return "redirect:/";
	}
	
	@GetMapping("/board/edit")
	public String itemEditPage(@RequestParam("num") int number, Model model, HttpSession session) {
		model.addAttribute("title", "수정하기");
		model.addAttribute("path", "/board/edit"); 
		model.addAttribute("content", "editFragment"); 
		model.addAttribute("contentHead", "editFragmentHead");
		
		model.addAttribute("board", boardService.get(number));
		return "/basic/layout";
	}
	
	@PostMapping("/board/edit")
	public String itemEdit(@RequestParam Map<String, String> data) {
		System.out.println(data.get("edit-id"));
		String title = data.get("title");
		String content = data.get("content");
		int id = Integer.parseInt(data.get("edit-id"));
		boardService.edit(title, content, id);		
		return "redirect:/board/item?num="+id;
	}
	
	@PostMapping("/upload")
	@ResponseBody
	public ModelMap uploadImage(MultipartHttpServletRequest req) {
		ModelMap model = new ModelMap();
		try {
			MultipartFile uploadFile = req.getFile("upload");
			String originName = uploadFile.getOriginalFilename();
			String[] tempNames = originName.split("[.]");
			String ext = "."+tempNames[tempNames.length - 1];
//			String ext = originName.substring(originName.indexOf("."));
			String randomName = UUID.randomUUID() + ext;
			String savePath = "C:\\Users\\KGA\\git\\board\\src\\main\\resources\\static\\Imgs\\"+randomName;
			String uploadUrl = "/imgs/" + randomName;
			File file = new File(savePath);
			uploadFile.transferTo(file);
			
			model.addAttribute("uploaded",true);
			model.addAttribute("url",uploadUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return model;
	}
}
