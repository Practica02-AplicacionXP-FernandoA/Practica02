package madstodolistfaldaz.controller;

import madstodolistfaldaz.authentication.ManagerUserSession;
import madstodolistfaldaz.dto.UsuarioData;
import madstodolistfaldaz.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManagerUserSession managerUserSession;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    public void testAboutPageAuthenticated() throws Exception {
        Long userId = 1L;
        UsuarioData usuario = new UsuarioData();
        usuario.setId(userId);
        usuario.setNombre("Test User");

        when(managerUserSession.usuarioLogeado()).thenReturn(userId);
        when(usuarioService.findById(userId)).thenReturn(usuario);

        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"))
                .andExpect(model().attribute("isAuthenticated", true))
                .andExpect(model().attribute("usuario", usuario));
    }

    @Test
    public void testAboutPageUnauthenticated() throws Exception {
        when(managerUserSession.usuarioLogeado()).thenReturn(null);

        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"))
                .andExpect(model().attribute("isAuthenticated", false))
                .andExpect(model().attributeDoesNotExist("usuario"));
    }

    @Test
    public void testAccountPageAuthenticated() throws Exception {
        Long userId = 1L;
        UsuarioData usuario = new UsuarioData();
        usuario.setId(userId);

        when(managerUserSession.usuarioLogeado()).thenReturn(userId);
        when(usuarioService.findById(userId)).thenReturn(usuario);

        mockMvc.perform(get("/account"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuarios/1/tareas"));
    }

    @Test
    public void testAccountPageUnauthenticated() throws Exception {
        when(managerUserSession.usuarioLogeado()).thenReturn(null);

        mockMvc.perform(get("/account"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}