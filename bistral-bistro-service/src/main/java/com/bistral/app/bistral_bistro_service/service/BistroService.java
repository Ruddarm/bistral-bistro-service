package com.bistral.app.bistral_bistro_service.service;

import com.bistral.app.bistral_bistro_service.contexts.UserContextHolder;
import com.bistral.app.bistral_bistro_service.dtos.*;
import com.bistral.app.bistral_bistro_service.entity.Bistro;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceNotFoundException;
import com.bistral.app.bistral_bistro_service.mapperInterface.BistroMapper;
import com.bistral.app.bistral_bistro_service.projection.BistroBranchContextProjection;
import com.bistral.app.bistral_bistro_service.repository.BistroRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BistroService {

    private final BistroMapper bistroMapper;
    private final ModelMapper modelMapper;
    private final BistroRepository bistroRepository;

    public BistroResponse createBistro(BistroRequest bistroRequest) {
        Bistro bistro = bistroMapper.toEntity(bistroRequest);
        // TODO : User user form user context after authentciaton is implemented
        bistro.setUserId(UserContextHolder
                .getAuthContext().getUserId());
        bistroRepository.save(bistro);
        return bistroMapper.toResponse(bistro);
    }


    public List<BistroResponse> getAllBistroOfUser(UUID userId) {
        List<Bistro> bistroList = bistroRepository.findByUserId(userId);
        HashSet<UUID> bistrosSet = new HashSet<>();
        return bistroList
                .stream()
                .map((bistro) -> {
                    if (bistrosSet.contains(bistro.getBistroId())) return null;
                    bistrosSet.add(bistro.getBistroId());
                    List<BranchResponse> branchResponses = bistro.getBranches().stream().map(branch -> (BranchResponse.builder().branchId(branch.getBranchId()).branchName(branch.getBranchName())
                            .build())).toList();
                    BistroResponse bistroResponse = modelMapper.map(bistro, BistroResponse.class);
//                    System.out.println();
                    bistroResponse.setBranchResponses(branchResponses);
                    return bistroResponse;
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public Bistro getBistroEntityById(UUID bistroId) throws ResourceNotFoundException {
        return bistroRepository.findById(bistroId)
                .orElseThrow(() -> new ResourceNotFoundException("Bistro", "Bistro not found with Id " + bistroId));
    }

    public BistroResponse getBistroResponseByBistroID() throws ResourceNotFoundException {
        Bistro bistro = bistroRepository.findByBistroId(
                UserContextHolder.getAuthContext().getBistroId()
        ).orElseThrow(
                () -> new ResourceNotFoundException("Bistro", "Bistro not found with Id " + UserContextHolder
                        .getAuthContext().getBistroId())
        );
        return BistroResponse.builder().bistroName(bistro.getBistroName())
                .bistroId(bistro.getBistroId())
                .branchResponses(bistro.getBranches()
                        .stream().map((branchEntity -> modelMapper.map(branchEntity, BranchResponse.class))).toList())
                .build();
    }

    public Boolean existByBistroId(UUID bistroId) {
        return bistroRepository.existsById(bistroId);
    }

    /*
        A method to get a list of all branch for a particular bistro
        @Param bistroId
        @Return @link{List<BranchResponse>} return a list of all branches
     */
    public List<BranchResponse> getListOfBranches() throws ResourceNotFoundException {
        Bistro bistro = bistroRepository.findBistroWithBranches(
                UserContextHolder.getAuthContext().getBistroId()
        ).orElseThrow(
                () -> new ResourceNotFoundException("", "bistro not found")
        );
        return bistro.getBranches()
                .stream()
                .map((branch) -> modelMapper.map(branch, BranchResponse.class))
                .collect(Collectors.toList());
    }

    /*
        A method to get a list of all menus for a bistro

     */
    @Deprecated
    public List<MenuResponse> getListOfMenus() throws ResourceNotFoundException {
        Bistro bistro = getBistroEntityById(UserContextHolder
                .getAuthContext().getBistroId());
        return bistro.getMenuEntities()
                .stream()
                .map((menu) -> modelMapper.map(menu, MenuResponse.class))
                .collect(Collectors.toList());
    }

    public List<BistroWithMenus> getListOfBistroWithMenus(UUID userId) throws ResourceNotFoundException {
        List<Bistro> bistroEntities = bistroRepository.findAllBistroWithMenuByUserId(userId);
        return bistroEntities.stream()
                .map(b -> BistroWithMenus
                        .builder()
                        .BistroId(b.getBistroId())
                        .BistroName(b.getBistroName())
                        .menuResponseList(b.getMenuEntities()
                                .stream()
                                .map(menuEntity -> MenuResponse.builder()
                                        .menuName(menuEntity.getMenuName())
                                        .menuId(menuEntity.getMenuId()).build()
                                ).toList())
                        .build()).toList();

    }


    public List<BistroContextDto> getListOfBistroContext(List<UUID> bistroIds, List<UUID> branchIds) {
        List<BistroBranchContextProjection> bistroBranchContextProjections =
                bistroRepository.getBistroContext(bistroIds, branchIds);

        Map<UUID, BistroContextDto> bistroContextMap = new HashMap<>();
        bistroBranchContextProjections
                .forEach((bistroContext) -> {
                    bistroContextMap.computeIfAbsent(bistroContext.getBistroId(),
                            (bistroId) ->
                            {
                                return BistroContextDto
                                        .builder()
                                        .bistroId(bistroId)
                                        .bistroName(bistroContext.getBistroName())
                                        .branches(new ArrayList<>()).build();
                            });

                    bistroContextMap.get(bistroContext.getBistroId())
                            .getBranches().add(BranchContextDto.builder()
                                    .branchId(bistroContext.getBranchId())
                                    .branchName(bistroContext.getBranchName())
                                    .build());
                });

        return bistroContextMap.values().stream().toList();
    }
}
