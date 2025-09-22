# display - 디스플레이 엔티티 사용을 돕는 라이브러리

## Paper API 사용(Paper API 미지원 버킷 사용 불가)

[![Maven Central](https://img.shields.io/maven-central/v/io.github.haburashi76/display-core)](https://central.sonatype.com/artifact/io.github.haburashi76/display-core)

---
* ## 주요 기능
  * ### 디스플레이 그룹으로 묶어 관리
    * #### move, rotate 등 동작
  * ### 디스플레이 간편히 생성
여타 기능 업데이트 예정

* ## Gradle 의존성 추가

```kotlin
repositories {
    mavenCentral()
}
dependencies {
  //최신 버전 사용 권장!!! 
  //!!"api" 사용!!
  implementation("io.github.haburashi76:display-api:<version>")
}
```

* ## plugin.yml 라이브러리 추가
```yaml
#최신 버전 권장!!!
#!!"core" 사용!!
name: ...
version: ...
main: ...
libraries:
  - io.github.haburashi76:display-core:<version>
```

* ## 유의할 점
  * ### fatJar(shadowJar)로 빌드하면 문제 생길 수 있음

---

* ## 추가
  * ### 라이센스는 GPL-3.0입니다.
  * 사실 내 쓰레기 코드 가지고 뭔짓 할 사람 없긴 함
