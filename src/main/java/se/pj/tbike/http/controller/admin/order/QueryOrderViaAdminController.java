package se.pj.tbike.http.controller.admin.order;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('ADMIN')")
@RequestMapping()
@RestController
@RequiredArgsConstructor
public class QueryOrderViaAdminController {
}
