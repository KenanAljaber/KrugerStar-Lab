package com.krugerstarlab.seeders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.krugerstarlab.entity.member.Group;
import com.krugerstarlab.entity.member.Member;
import com.krugerstarlab.entity.member.Role;

public class MemberGenerator {
	
	

    public static List<Member> generateMembers(PasswordEncoder passwordEncoder,File namesFile, int numMembers) throws FileNotFoundException {
        List<String> firstNames = new ArrayList<>();
        Scanner scanner = new Scanner(namesFile);
        while (scanner.hasNextLine()) {
            firstNames.add(scanner.nextLine().trim());
        }
        scanner.close();

        List<Member> members = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < numMembers; i++) {
            String firstName = firstNames.get(rand.nextInt(firstNames.size()));
            String lastName = LAST_NAMES[rand.nextInt(LAST_NAMES.length)];
            String email = firstName.toLowerCase() + "." +i+ lastName.toLowerCase() + "@example.com";
            String phoneNumber = generatePhoneNumber(rand);
            String password = passwordEncoder.encode("12345678");
            String photo = "photo-" + rand.nextInt(100) + ".jpg";
            Role role = Role.values()[rand.nextInt(Role.values().length)];
            Member member = new Member(firstName, lastName, email, phoneNumber, password, photo, role);
            member.setGithubLink("https://github.com/"+member.getFirstName());
            member.setLinkedinLink("https://linkedin.com/"+member.getFirstName());
            members.add(member);
        }

        return members;
    }
    
    
    
    public static List<Group> groupsGenerator(PasswordEncoder passwordEncoder,File groupsfile,int numGroups) throws FileNotFoundException {
    	 List<String> groupsNames = new ArrayList<>();
    	 List<Group> groups=new ArrayList<>();
         Scanner scanner = new Scanner(groupsfile);
         while (scanner.hasNextLine()) {
        	 groupsNames.add(scanner.nextLine().trim());
         }
         scanner.close();
         File namesFile = new File("src/main/resources/static/names.txt");
         Random rand = new Random();
         for (int i=0;i<numGroups;i++) {
        	   List<Member> members=generateMembers(passwordEncoder,namesFile, 4);
        	   String gName=groupsNames.get(rand.nextInt(groupsNames.size()))+""+i;
        	   Group g=new Group(gName);
        	   members.forEach(it-> g.addMember(it));
        	   groups.add(g);
         }
         return groups;
    	
    }

    private static String generatePhoneNumber(Random rand) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Young", "King", "Allen", "Wright", "Scott", "Green", "Baker", "Adams", "Nelson", "Carter", "Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins", "Stewart", "Sanchez", "Morris", "Rogers", "Reed", "Cook", "Morgan", "Cooper", "Bell", "Murphy", "Bailey", "Rivera", "Hill", "Ramirez", "Reyes", "Long", "Foster", "Sanders", "Ross", "Morales", "Powell", "Sullivan",
    		"Russell", "Ortiz", "Jenkins", "Gomez", "Murray", "Freeman", "Wells",
    		"Webb", "Simpson", "Stevens", "Tucker", "Porter", "Hunter", "Hicks", "Crawford", "Henry", "Boyd",
    		"Mason", "Mills", "Warren", "Fox", "Rose", "Rice", "Burns", "Gordon", "Shaw", "Holmes", "Ramos", "Ferguson", "Fletcher", "Grant", "Knight", "Simpson", "Reyes", "Henderson", "Ford", "Marshall",
    		"Carpenter", "Weaver", "Coleman", "Jordan", "Owens", "Reynolds", "Graham", "Lawrence", "Nichols",
    		"Wallace", "Dean", "Stone", "Hayes", "West", "Jordan", "Barnes", "Austin", "Haw"};
    }
