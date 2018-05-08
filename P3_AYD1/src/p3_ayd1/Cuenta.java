package p3_ayd1;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import dto.Cuenta_dto;
import dto.Usuario_dto;

public class Cuenta extends Utilidad {
	
	public Label lbldcuenta, lbldnombre, lbldusuario, lbldcodigo,lblmonto;
	public Window wcuenta;
	public Button btnsalir;
	public Textbox txtocuenta, txtmonto;
	public Textbox txtpUsuario,txtpNombre, txtpEmail,txtpContrasenia;
	public Label lblpUsuario, lblpNombre, lblpEmail, lblpContrasenia,lblrperfil;
	public Textbox txtmacreditar, txtadescripcion;
	Cuenta_dto dc;
	Usuario_dto du;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		dc = (Cuenta_dto)Sessions.getCurrent().getAttribute("cuenta");
		 du = (Usuario_dto)Sessions.getCurrent().getAttribute("usuario");
		lbldcuenta.setValue(""+dc.getNO_CUENTA().intValue());
		lbldcodigo.setValue(du.getCOD_USUARIO().toString());
		lbldnombre.setValue(du.getNOMBRE().toString());
		lbldusuario.setValue(du.getUSUARIO().toString());	
		lblmonto.setValue(dc.getSALDO().toString());
		
		txtpUsuario.setValue(du.getUSUARIO());
		txtpNombre.setValue(du.getNOMBRE());
		txtpEmail.setValue(du.getEMAIL());
		txtpContrasenia.setValue(du.getCONTRASENIA());
		

	}
	
	public void onClick$btnacreditar(){
		
		
	}
	
	
	public void onClick$btnPerfil() throws Exception{
		limpiar();
		int res =verificarCampos(txtpNombre.getValue(), txtpUsuario.getValue(),
				txtpContrasenia.getValue(),txtpEmail.getValue());
		if(res==0){
			
				int consul =actualizarperfil(txtpUsuario.getValue(), txtpNombre.getValue(),
						txtpEmail.getValue(),txtpContrasenia.getValue(),du.getCOD_USUARIO().intValue());
				if(consul==0){
					lblrperfil.setValue("Perfil actualizado exitosamente");
					obtieneusuario(du.getUSUARIO());
					doAfterCompose(wcuenta);
				}else{
					lblrperfil.setValue("No se ha podido actualizar el perfil");
				}
			
			
		}
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	private void obtieneusuario(String usu) throws SQLException{		
		String sql ="SELECT COD_USUARIO, USUARIO, NOMBRE, EMAIL, CONTRASENIA, ROL "+
						"FROM USUARIO U "+
						" WHERE U.USUARIO='"+usu+"' ";
		BeanListHandler<Usuario_dto> auxiliarBean = new BeanListHandler<Usuario_dto>(Usuario_dto.class);
		List<Usuario_dto> auxiliarLista = new ArrayList<Usuario_dto>();
		auxiliarLista  =  Utilidad.ejecutaConsultaList(sql, auxiliarBean);				
		System.out.println("tamaño lista: "+auxiliarLista.get(0).getNOMBRE());
		Sessions.getCurrent().setAttribute("usuario", auxiliarLista.get(0));
		
		
	}
	
	private void limpiar(){
		lblrperfil.setValue("");
		lblpContrasenia.setValue("");
		lblpEmail.setValue("");
		lblpNombre.setValue("");
		lblpUsuario.setValue("");
		
	}
	
	public void onClick$btntransferir(){
		
		
	}
	
	
	
	private int actualizarperfil(String usu, String nombre, String email,
			String pass, int cod_usu) throws SQLException{
		int res=0;
		String sql =" UPDATE USUARIO "+
				"SET USUARIO='"+usu+"' , "+ 
				"NOMBRE ='"+nombre+"', "+
				"EMAIL='"+email+"', "+
				"CONTRASENIA='"+pass+"' "+
				" WHERE COD_USUARIO="+cod_usu;
		Object ret = Utilidad.ejecutaUpdate(sql);
		System.out.println("valor de consulta:"+ret);
		if(ret.equals("")||!ret.toString().equals("1")){
			res=1;			
		}	
		return res;
		
	}
	
	
	
	private int verificarCampos(String nombre, String usu, String pass, String email){
		int res=0;
		//se verifican que todos los campos esten llenos de info
		if(nombre==null || nombre.isEmpty()||nombre.toString().trim().equals("")){			
			lblpNombre.setValue("Debe ingresar informacion");
			res++;
		}
		if(usu==null || usu.isEmpty()||usu.toString().trim().equals("")){			
			lblpUsuario.setValue("Debe ingresar informacion");
			res++;
		}
		if(pass==null || pass.isEmpty()||pass.toString().trim().equals("")){			
			lblpContrasenia.setValue("Debe ingresar informacion");
			res++;
		}
		
		if(email==null || email.isEmpty()||email.toString().trim().equals("")){			
			lblpEmail.setValue("Debe ingresar informacion");
			res++;
		}
		
		
		return res;
	}

}
