package madstodolistfaldaz.controller;

import madstodolistfaldaz.authentication.ManagerUserSession;
import madstodolistfaldaz.dto.UsuarioData;
import madstodolistfaldaz.model.Usuario;
import madstodolistfaldaz.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import madstodolistfaldaz.repository.UsuarioRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class HomeController {

    @Autowired
    private ManagerUserSession managerUserSession;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/about")
    public String about(Model model) {
        Long idUsuarioLogeado = managerUserSession.usuarioLogeado();
        addUserDataToModel(model, idUsuarioLogeado);
        return "about";
    }

    @GetMapping("/account")
    public String account(Model model) {
        Long idUsuarioLogeado = managerUserSession.usuarioLogeado();

        if (idUsuarioLogeado == null) {
            return "redirect:/login";
        }

        UsuarioData usuario = usuarioService.findById(idUsuarioLogeado);
        model.addAttribute("usuario", usuario);

        return "redirect:/usuarios/" + idUsuarioLogeado + "/tareas";
    }

    @GetMapping("/registrados")
    public String listarUsuarios(Model model) {
        Long idUsuarioLogeado = managerUserSession.usuarioLogeado();

        // Verificar si el usuario está autenticado
        if (idUsuarioLogeado == null) {
            return "redirect:/login";
        }

        // Obtener datos del usuario logueado para el navbar
        addUserDataToModel(model, idUsuarioLogeado);

        // Obtener todos los usuarios registrados y convertirlos a List
        Iterable<Usuario> usuariosIterable = usuarioRepository.findAll();
        List<Usuario> usuarios = StreamSupport.stream(usuariosIterable.spliterator(), false)
                .collect(Collectors.toList());
        model.addAttribute("usuarios", usuarios);

        return "registrados";
    }

    // Método auxiliar para añadir datos del usuario al modelo
    private void addUserDataToModel(Model model, Long idUsuarioLogeado) {
        if (idUsuarioLogeado != null) {
            UsuarioData usuario = usuarioService.findById(idUsuarioLogeado);
            model.addAttribute("usuario", usuario);
            model.addAttribute("isAuthenticated", true);
        } else {
            model.addAttribute("isAuthenticated", false);
        }
    }
}