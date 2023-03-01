package com.krugerstarlab.seeders;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.krugerstarlab.entity.Post;
import com.krugerstarlab.entity.comment.Comment;
import com.krugerstarlab.entity.project.ProjectPost;
import com.krugerstarlab.entity.project.ProjectType;
import com.krugerstarlab.service.Post.PostService;
import com.krugerstarlab.service.comment.CommentService;
import com.krugerstarlab.service.project_post.ProjectPostService;

@Component
public class PostsAndProjectsSeeder {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private ProjectPostService ppService;
	
	@Autowired
	private CommentService cService;
	
	public void generatePosts() {
		
		try {
			List<Post> posts=getPostsFromFile();
			posts.forEach(it-> {
				System.out.println(it.toString());
				Post p=postService.createPost(it);
				p.addComment(new Comment(2L,"ok this is a good post ",p.getId()));
				postService.updatePost(p.getId(), p);
			
			});
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}
		
		
	}
	
	public void generateProjectPosts() {
		
		try {
			List<ProjectPost> posts=getProjectsFromFile();
			posts.forEach(it-> {
				System.out.println(it.toString());
				ppService.createProjectPost(it);});
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}
		
	}
	
	private List<Post> getPostsFromFile() throws FileNotFoundException{
			List<Post> posts=new ArrayList<>();
			List<String> postsStr=new ArrayList<>();
			File file =new File("src/main/resources/static/postsContent.txt");
			
			Scanner scn=new Scanner(file);
			while(scn.hasNextLine()) {
				postsStr.add(scn.nextLine());
			}
			scn.close();
			Random rand=new Random();
			for(int i=0;i<postsStr.size();i++) {
				Post p=new Post(rand.nextLong(1, 6),postsStr.get(i));
				posts.add(p);
			}
			return posts;
			
		
	}
	
	private List<ProjectPost> getProjectsFromFile() throws FileNotFoundException{
		List<ProjectPost> posts=new ArrayList<>();
		List<String> postsStr=new ArrayList<>();
		File file =new File("src/main/resources/static/projectsContent.txt");
		
		Scanner scn=new Scanner(file);
		while(scn.hasNextLine()) {
			postsStr.add(scn.nextLine());
		}
		scn.close();
		Random rand=new Random();
		for(int i=0;i<postsStr.size();i++) {
			ProjectPost p=new ProjectPost( LocalDate.of(2023, 5, 8),1L,postsStr.get(i),
					ProjectType.values()[rand.nextInt(ProjectType.values().length)]);
			posts.add(p);
		}
		return posts;
		
	
}

}
