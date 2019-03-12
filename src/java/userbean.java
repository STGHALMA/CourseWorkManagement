import static com.sun.faces.el.FacesCompositeELResolver.ELResolverChainType.Faces;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@Named(value = "userbean")
@SessionScoped
public class userbean implements Serializable {
    private String username;
    private String password;
    private String repassword;
    private String secans;
    private int sectype;
    private Part uploadedFile;
    private ArrayList<String> list = new ArrayList<String>();
    private String path = System.getProperty("user.dir");
    private String filename;
    private String date;
    private ArrayList<String> selectedArrayList = new ArrayList<String>();
    public userbean() {
        username = "";
        password = "";
        repassword = "";
        secans = "";
        sectype = 1;
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getSecans() {
        return secans;
    }

    public void setSecans(String secans) {
        this.secans = secans;
    }

    public int getSectype() {
        return sectype;
    }

    public void setSectype(int sectype) {
        this.sectype = sectype;
    }

    public String validateUsernamePassword() {
		boolean valid = Login.validate(username, password);
                String sessionusername = null;
		if (valid) {
                        if("admin".equals(username) && "1234".equals(password)){
                    return "adminHomepage";
                }
			return "studentHomepage";}
		 else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username and Passowrd",
							"Please enter correct username and Password"));
			return "index";
		}
	}

    //logout event, invalidate session
    public String logout() {
            return "index";
	}
    public String register() throws SQLException{
      boolean doesExist = Register.checkExist(username);
        if(doesExist){
            FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Username already exists",
							"Please enter different username"));
			return "register";
        }
        else{
           int uid = Register.countUID();
           uid = uid + 1;
           Register.register(uid, username, password, secans);
        }
        return "index";
    }
    
    public String forgot(){
        boolean valid = Forgot.validateUsernameSecans(username, secans);
        if(valid){
           return "reset";
        }
        else
        {
            FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Either wrong username or security",
							"Please enter correct information"));
			
        }
        return "forgot";
    }
    public String reset(){
        if(password.equals(repassword)){
        Reset.resetPassword(username, password);
        logout();
        }
        else{
        FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    "Password mismatch",
                                                    "Please enter correct information"));
        }
        return "index";
    }
    public String add()
    {
        return "add";
    }

    /**
     * @return the uploadedFile
     */
    public Part getUploadedFile() {
        return uploadedFile;
    }

    /**
     * @param uploadedFile the uploadedFile to set
     */
    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    
    public String saveFile(){
    try (InputStream input = uploadedFile.getInputStream()) {
    String fileName = uploadedFile.getSubmittedFileName();
    fileName = fileName.substring((fileName.lastIndexOf("\\")+1), fileName.length());
    fileName += "-" + this.date;
            Files.copy(input, new File("C:\\Assignments\\" + fileName).toPath());
            listfilename();
            return "adminHomepage";
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "adminHomepage";
    }
    
    public String saveAssignment(){
    try (InputStream input = uploadedFile.getInputStream()) {
    String fileName = uploadedFile.getSubmittedFileName();
    fileName = fileName.substring((fileName.lastIndexOf("\\")+1), fileName.length());
            Files.copy(input, new File("C:\\Student\\" + fileName).toPath());
            listfilename();
            return "studentHomepage";
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "studentHomepage";
    }
    

    public void downloadAssignment() throws IOException
{
    int count = 0;
        while(count!=selectedArrayList.size())
        {
            
    File file = new File("C:\\Assignments\\" + selectedArrayList.get(count));

    FacesContext facesContext = FacesContext.getCurrentInstance();

    HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    response.reset();
    response.setHeader("Content-Type", "application/octet-stream");
    response.setHeader("Content-Disposition", "attachment;filename=" + selectedArrayList.get(count));

    OutputStream responseOutputStream = response.getOutputStream();

    InputStream fileInputStream = new FileInputStream(file);

    byte[] bytesBuffer = new byte[2048];
    int bytesRead;
    while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) 
    {
        responseOutputStream.write(bytesBuffer, 0, bytesRead);
    }

    responseOutputStream.flush();

    fileInputStream.close();
    responseOutputStream.close();

    facesContext.responseComplete();
    count++;
        }
}

    
    
    public void listfilename(){
        list.clear();
        File dir = new File("C:\\Assignments");
        File[] files = dir.listFiles();
        if (files.length == 0) {
            list.add("No assignments uploaded");
        } else {
            for (File aFile : files) {
            list.add(aFile.getName());
            }
        }
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
    

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public String deleteFile(){
        int count = 0;
        while(count!=selectedArrayList.size())
        {
        try{
            String fileString = selectedArrayList.get(count);
    		File file = new File("C:\\Assignments\\"+fileString);
    		if(file.delete()){
                      System.out.print("File Deleted!");
    		}else{
    			FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    "Something went wrong!",
                                                    "Please enter correct information"));
                } 	   
    	}catch(Exception e){
    		e.printStackTrace();
    		
    	}
        count++;
        }
                        listfilename();
    			return "adminHomepage";
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the selectedArrayList
     */
    public ArrayList<String> getSelectedArrayList() {
        return selectedArrayList;
    }

    /**
     * @param selectedArrayList the selectedArrayList to set
     */
    public void setSelectedArrayList(ArrayList<String> selectedArrayList) {
        this.selectedArrayList = selectedArrayList;
    }
}    
