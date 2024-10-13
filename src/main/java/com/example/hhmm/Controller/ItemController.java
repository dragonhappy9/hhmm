package com.example.hhmm.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.hhmm.Entity.Item;
import com.example.hhmm.Repository.ItemRepository;

import jakarta.annotation.PostConstruct;

import java.util.List;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

    // 모델 전체 불러오기
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "/items";
    }

    // itemId로 검색
    @GetMapping("/{itemId}")
    public String item(Model model, @PathVariable Long itemId) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "/item";
    }

    // add폼
    @GetMapping("/add")
    public String addForm() {
        return "addForm";
    }
    

    // RequestParam으로 요청 파라미터 데이터를 해당 변수에 받는다. 
    // Item 객체를 생성해 전달받은 파라미터로 값을 세팅한 뒤 저장한다.
    // 저장된 아이템을 Model 객체에 담아 뷰에 전달한다.
    /*  
    @PostMapping("/add")
    public String saveLegacy(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        Item save = itemRepository.save(item);
        model.addAttribute("item", save);
        return "item";
    }
    */

    //     @ModelAttribute 애노테이션을 활용해 요청 파라미터를 처리해준다.
    // ⇒ Item 객체를 생성 후, 요청 파라미터의 값을 프로퍼티 접근법(setXxx)으로 입력해준다.
    // @ModelAttribute - Model 자동 추가
    // ⇒ 위 코드를 보면 Model 객체에 저장된 item을 추가해주는 로직을 주석처리했다. 이는 @ModelAttribute 애노테이션의 기능 덕분인데, 이 기능으로 바로 Model에 @ModelAttribute로 지정한 객체를 자동으로 넣어준다. 
    // 모델에 데이터를 담을때는 이름이 필요한데 이름은 애노테이션에 지정한 속성("item")을 사용한다.
    /* 
    @PostMapping("/add")
    public String saveV2(@ModelAttribute("item") Item item, Model model) {
        Item save = itemRepository.save(item);
        model.addAttribute("item", save); //생략 가능
        return "item";
    }*/ 

    // @ModelAttribute 애노테이션에서 name 속성을 생략할수도 있다.
    // ⇒ 생략하면 모델에 저장될 때 클래스명에서 첫 글자를 소문자로 변경해 등록한다.
    // ⇒ @ModelAttribute Apple apple이면 Model.addAttribute("apple", apple) 와 같다.
    /*
    @PostMapping("/add")
    public String saveV3(@ModelAttribute Item item, Model model) {
        itemRepository.save(item);
        //model.addAttribute("item", save);
        return "item";
    }*/

    // 심지어 @ModelAttribute 애노테이션도 생략이 가능하다. 대상 객체가 모델에 자동등록되는 기능도 정상동작한다. 
    // 객체가 아니라 기본타입이면 @RequestParam이 동작한다.
    /* 
    @PostMapping("/add")
    public String saveV4(Item item) {
        itemRepository.save(item);
        return "item";
    }*/

    // 리다이렉트
    /*
    @PostMapping("/add")
    public String saveV5(Item item) {
        itemRepository.save(item);

        return "redirect:/items/" + item.getId();
    }*/

    // 리다이렉트를 통해 페이지를 이동하는 것은 좋은데, 
    // 이 경우 내가 수행한 로직(상품 등록, 상품 수정 등) 이 
    // 정상적으로 완료되었는지를 알 수 없다. 
    // 그래서 리다이렉트 된 페이지에 이런 결과를 노출하고싶을 때 
    // RedirectAttributes를 이용하면 된다.
    @PostMapping("/add")
    public String saveV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/items/{itemId}";
    }
}