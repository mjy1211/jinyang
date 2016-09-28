package crawlingDouban;

public class Book implements Comparable<Book> {
	
	private String name;
  private int ratingCount;
  private double rating;
	
	public Book(){
		
	}
	
	public Book(String name, int ratingCount, double rating){
		this.name = name;
		this.ratingCount = ratingCount;
		this.rating = rating;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getRatingCount(){
		return this.ratingCount;
	}
	
	public double getRating(){
		return this.rating;
	}
	
	@Override
	public String toString(){
		return (String.valueOf(this.rating) + " " + String.valueOf(this.ratingCount) + " " + this.name);
	}
  
	/*
	 * Ovrride使数据可以正确排序
	 */
	@Override
	public int compareTo(Book book) {
		if(this.getRating()<book.getRating()) 
			return -1;  
		else if(this.getRating()>book.getRating()) 
			return 1;  
		return 0;
	}
	
}
