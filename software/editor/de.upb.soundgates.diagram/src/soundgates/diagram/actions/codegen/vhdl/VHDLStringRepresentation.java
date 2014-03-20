package soundgates.diagram.actions.codegen.vhdl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class VHDLStringRepresentation extends ArrayList<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4668218210419623058L;
	
	private static String delimiter = " ";
	
	public boolean add(String elem){
		return super.add(elem);		
	}
	
	public boolean add(VHDLStringRepresentation rep){
				
		return this.addAll(rep);		
	}
	
	public boolean addLine(String elem){
		return super.add(elem + "\n");
	}
	
	public VHDLStringRepresentation pack(){
		VHDLStringRepresentation rep = new VHDLStringRepresentation();
	
		String line = "";
		
		for(String elem : this){
			line += elem;
			
			if(elem.endsWith("\n")){				
				rep.add(line);
				line = "";
			}
		}
		
		if(line.length() > 0){
			rep.add(line);
		}
		
		return rep;
	}
	
	
	/** 
	 * Indent all lines by n
	 * 
	 * @param n number of spaces to indent
	 */
	public VHDLStringRepresentation indent(int n){
		String indent = getIndentString(n);
		
		ListIterator<String> iter = this.listIterator();
		
		while(iter.hasNext()){
			String elem = iter.next();
			iter.set(indent + elem);
		}
		
		return this;
	}
	
	/**
	 * Returns the offset to a line inside the representation
	 * 
	 * @param keyword	keyword that marks a line 
	 * @return	offset to the line that contains the keyword, -1 otherwise
	 */
	public int[] getStringOffset(String keyword){
		ListIterator<String> iter 		   = this.listIterator();
		ArrayList<Integer>	 offsetindices = new ArrayList<Integer>();
		int index;
		
		while(iter.hasNext()){
			
			index 		= iter.nextIndex();
			String elem = iter.next();
		
			if(elem.contains(keyword)){
				offsetindices.add(index);
			}
		}
		
		return convertIntegers(offsetindices);
	}
	
	/**
	 *	Indent a specified index by n 
	 * 
	 * @param n	number of spaces to insert to the right
	 * @param index index of the element to indent
	 */
	public void indent(int n, int index){
		String indent = getIndentString(n);
		
		String elem = this.get(index);
		this.set(index, indent + elem);
	}
	
	private String getIndentString(int length){
		String indent = "";
		while(length-- > 0){
			indent += delimiter;			
		}
		return indent;
	}
	
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		
		for(String elem : this){
			
			str.append(elem);
		}
		
		return str.toString();
	}
	
	
	public static int[] convertIntegers(List<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = integers.get(i).intValue();
	    }
	    return ret;
	}
}
