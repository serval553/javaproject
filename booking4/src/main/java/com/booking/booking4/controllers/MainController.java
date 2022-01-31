package com.booking.booking4.controllers;

import com.booking.booking4.service.Authorization;
import com.booking.booking4.service.WorkWithTicket;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/home")
    public String greeting(Model model){
        model.addAttribute("title", "Главная страница");
        return "home";
    }
    @GetMapping("/authorization")
    public String authorization(Model model){
        model.addAttribute("title", "Главная страница");
        return "authorization";
    }
    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("title", "Главная страница");
        return "registration";
    }
    @GetMapping("/ticket")
    public String ticket(Model model) throws SQLException{
        WorkWithTicket workWithTicket = new WorkWithTicket();
        List<String> allFilms = workWithTicket.allFilms();
        model.addAttribute("allFilms", allFilms);
        return "ticket";
    }
    @GetMapping("/ticket/{nameFilm}")
    public String bookingSessions(@PathVariable(value="nameFilm") String nameFilm, Model model) throws SQLException{
        WorkWithTicket workWithTicket = new WorkWithTicket();
        List<String> allDate = workWithTicket.allDate(nameFilm);
        model.addAttribute("nameFilm", nameFilm);
        model.addAttribute("allDate", allDate);
        return "ticketBookingDate";
    }

    @GetMapping("/ticket/{nameFilm}/{date}")
    public String bookingSessions(@PathVariable(value="nameFilm") String nameFilm,
                                  @PathVariable(value="date") String date, Model model) throws SQLException{
        WorkWithTicket workWithTicket = new WorkWithTicket();
        List<String> allTime = workWithTicket.allTime(nameFilm,date);
        model.addAttribute("nameFilm", nameFilm);
        model.addAttribute("date",date);
        model.addAttribute("allTime", allTime);
        return "ticketBookingTime";
    }

    @GetMapping("/ticket/{nameFilm}/{date}/{time}/buy")
    public String bookingSessions(@PathVariable(value="nameFilm") String nameFilm,
                                  @PathVariable(value="date") String date,
                                  @PathVariable(value="time") String time, Model model) throws SQLException{
        WorkWithTicket workWithTicket = new WorkWithTicket();
        Authorization authorization = new Authorization();
        if (workWithTicket.bookingTicket(authorization.getLogin(),workWithTicket. getIdFilm(nameFilm,date,time))){
            return "redirect:ticket";
        }
        return "redirect:s";
    }


    @PostMapping("/registration")
    public String registrationAdd(@RequestParam String login,@RequestParam String password, Model model) throws SQLException, ClassNotFoundException {
        Authorization authorization = new Authorization();
        authorization.createNewAccount(login,password);
        return "redirect:home";
    }

    @PostMapping("/authorization")
    public String authorizationAdd(@RequestParam String login, @RequestParam String password, Model model) throws SQLException, ClassNotFoundException {
        Authorization authorization = new Authorization();
        if (!authorization.loginUser(login,password)){
            return "redirect:home";
        }
        return "redirect:ticket";
    }
}
