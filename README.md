# hanghae-week2-homework
hanghae homework week2 / login post like




설명 해야할 내용

N + 1 문제와 해결법

연관 관계에서 발생하는 이슈로 연관 관계가 설정된 엔티티를 조회할 
경우에 조회된 데이터 갯수(n) 만큼 연관관계의 조회 쿼리가 
추가로 발생하여 데이터를 읽어오게 된다.
이를 N + 1 문제라고 한다.

Fetch join
사실 원하는 코드는 select * from Owner left join cat on cat.owner_id = owner 일 것이다.
최적화된 쿼리를 우리가 직접 사용할 수 있다. 하하지만
jpaRepository에서 제공해주는 것은 아니고 JPQL로 작성해야 한다.
@Query("select o from Oner o join fetch o.cats")
List<Owner> findAllJoinFetch();
  
  

레이지 로딩(Lazy Loading), 이거 로딩(Eager Loading)의 원리
요청하면 모든 작업을 수행합니다. 전형적인 예는 두 행렬을 돕할 때입니다. 모든 계산을 수행합니다.
레이지 로딩(Lazy Loading)
필요할 때만 계산을 수행합니다. 이전 예에서는 결과 행렬의 요소에 액세스할 때까지 계산을 수행하지 않습니다.



이거 로딩(Eager Loading)
