package madstodolistfaldaz.controller;

import madstodolistfaldaz.authentication.ManagerUserSession;
import madstodolistfaldaz.dto.LoginData;
import madstodolistfaldaz.dto.RegistroData;
import madstodolistfaldaz.dto.UsuarioData;
import madstodolistfaldaz.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/about")
    public String about(Model model) {
        // Verificar si hay usuario logueado
        Long idUsuarioLogeado = managerUserSession.usuarioLogeado();

        if (idUsuarioLogeado != null) {
            // Usuario logueado - obtener datos del usuario para el navbar
            UsuarioData usuario = usuarioService.findById(idUsuarioLogeado);
            model.addAttribute("usuario", usuario);
            model.addAttribute("isAuthenticated", true);
        } else {
            // Usuario no logueado
            model.addAttribute("isAuthenticated", false);
        }

        return "about";
    }

    /**
     * Página de gestión de cuenta (futura implementación)
     */
    @GetMapping("/account")
    public String account(Model model) {
        Long idUsuarioLogeado = managerUserSession.usuarioLogeado();

        if (idUsuarioLogeado == null) {
            return "redirect:/login";
        }

        UsuarioData usuario = usuarioService.findById(idUsuarioLogeado);
        model.addAttribute("usuario", usuario);

        // Por ahora redirigir a las tareas hasta implementar la página de cuenta
        return "redirect:/usuarios/" + idUsuarioLogeado + "/tareas";
    }
}