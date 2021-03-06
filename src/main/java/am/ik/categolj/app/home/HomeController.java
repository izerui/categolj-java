package am.ik.categolj.app.home;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import am.ik.categolj.app.common.component.PagerComponent;
import am.ik.categolj.domain.common.consts.Const;
import am.ik.categolj.domain.common.util.CommonUtils;
import am.ik.categolj.domain.model.Entry;
import am.ik.categolj.domain.service.entry.EntryService;

@Controller
public class HomeController {

    protected static final Logger logger = LoggerFactory
            .getLogger(HomeController.class);

    @Inject
    protected EntryService entryService;

    @Inject
    protected PagerComponent pager;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return page(Const.START_PAGE, model);
    }

    @RequestMapping(value = "/page/{page}/**", method = RequestMethod.GET)
    public String page(@PathVariable int page, Model model) {
        List<Entry> entries = entryService.getEntriesByPage(page,
                Const.VIEW_COUNT);
        int totalPage = CommonUtils.calcTotalPage(entryService
                .getTotalEntryCount());
        List<String> pagerLink = pager.createPaginationLinks(totalPage, page,
                entries, null);
        model.addAttribute(entries);
        model.addAttribute(Const.PAGER_ATTR, pagerLink);
        return "home";
    }
}
