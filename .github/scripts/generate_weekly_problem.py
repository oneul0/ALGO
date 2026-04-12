from pathlib import Path
import re
import sys

ROOT_README = Path("README.md")
TARGET_ROOT = Path("v.2.2")


def sanitize_folder_name(name: str) -> str:
    invalid_chars = r'<>:"/\\|?*'
    for ch in invalid_chars:
        name = name.replace(ch, "-")
    return name.strip()


def extract_required_section(text: str) -> str:
    """
    '## 필수 문제'부터 다음 주요 섹션 전까지 추출
    """
    pattern = re.compile(
        r"##\s*필수 문제\s*(.*?)(?=\n\s*###|\n\s*## |\Z)",
        re.DOTALL
    )
    match = pattern.search(text)
    if not match:
        raise ValueError("'## 필수 문제' 섹션을 찾을 수 없습니다.")
    return match.group(1).strip()


def extract_week_title(section: str) -> str:
    """
    필수 문제 섹션의 첫 번째 일반 텍스트 줄을 주차명으로 사용
    예: 4-3주간문제 디펜스
    """
    lines = [line.strip() for line in section.splitlines() if line.strip()]

    for line in lines:
        # 링크 줄 제외
        if re.match(r"^\[.+\]\(.+\)$", line):
            continue
        # html 줄 제외
        if line.startswith("<") and line.endswith(">"):
            continue
        return line

    raise ValueError("주차명을 찾을 수 없습니다.")


def extract_problem_links(section: str) -> list[str]:
    """
    필수 문제 섹션 내부의 마크다운 링크 줄만 추출
    """
    links = []
    for line in section.splitlines():
        stripped = line.strip()
        if re.match(r"^\[.+\]\(.+\)$", stripped):
            links.append(stripped)

    if not links:
        raise ValueError("문제 링크를 찾을 수 없습니다.")

    return links


def build_readme_content(week_title: str, problem_links: list[str]) -> str:
    lines = [
        f"# {week_title}",
        "",
        "## 필수 문제",
        ""
    ]
    lines.extend(f"- {link}" for link in problem_links)
    lines.append("")
    return "\n".join(lines)


def main():
    if not ROOT_README.exists():
        print("루트 README.md가 없습니다.")
        sys.exit(1)

    text = ROOT_README.read_text(encoding="utf-8")

    required_section = extract_required_section(text)
    week_title = sanitize_folder_name(extract_week_title(required_section))
    problem_links = extract_problem_links(required_section)

    target_dir = TARGET_ROOT / week_title
    target_dir.mkdir(parents=True, exist_ok=True)

    target_readme = target_dir / "README.md"
    new_content = build_readme_content(week_title, problem_links)

    old_content = ""
    if target_readme.exists():
        old_content = target_readme.read_text(encoding="utf-8")

    if old_content == new_content:
        print("변경 사항 없음")
        return

    target_readme.write_text(new_content, encoding="utf-8")
    print(f"생성 완료: {target_readme}")


if __name__ == "__main__":
    main()
