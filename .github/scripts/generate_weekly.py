from pathlib import Path
import re
import sys

ROOT_README = Path("README.md")
TARGET_ROOT = Path("v.2.2")


def sanitize(name: str) -> str:
    invalid = r'<>:"/\\|?*'
    for ch in invalid:
        name = name.replace(ch, "-")
    return name.strip()


def extract_week_block(text: str):
    pattern = re.compile(
        r"^###\s*🟨\s*(.+?)\s*$([\s\S]*?)(?=^###\s|^##\s|\Z)",
        re.MULTILINE
    )

    match = pattern.search(text)
    if not match:
        raise ValueError("README에서 '### 🟨 주차명' 형식의 블록을 찾을 수 없습니다.")

    title = match.group(1).strip()
    body = match.group(2)

    return title, body


def extract_links(body: str):
    links = []
    for line in body.splitlines():
        stripped = line.strip()
        if re.match(r"^\[.+\]\(.+\)\s*$", stripped):
            links.append(stripped)

    if not links:
        raise ValueError("주차 블록 안에서 문제 링크를 찾지 못했습니다.")

    return links


def parse_markdown_link(link: str):
    match = re.match(r"^\[(.+)\]\((.+)\)\s*$", link)
    if not match:
        raise ValueError(f"올바른 마크다운 링크 형식이 아닙니다: {link}")
    title = match.group(1).strip()
    url = match.group(2).strip()
    return title, url


def detect_platform_tag(url: str) -> str:
    url_lower = url.lower()

    if "acmicpc.net" in url_lower:
        return "BOJ"
    if "programmers.co.kr" in url_lower or "school.programmers.co.kr" in url_lower:
        return "PGS"
    if "swexpertacademy.com" in url_lower:
        return "SWEA"
    if "leetcode.com" in url_lower:
        return "LTC"

    return "ETC"


def build_weekly_readme(title: str, links: list[str]) -> str:
    lines = [
        f"# {title}",
        "",
        "## 필수 문제",
        ""
    ]

    for link in links:
        lines.append(f"- {link}")

    lines.append("")
    return "\n".join(lines)


def build_problem_folder_name(problem_title: str, url: str) -> str:
    tag = detect_platform_tag(url)
    return sanitize(f"[{tag}] {problem_title}")


def main():
    if not ROOT_README.exists():
        print("루트 README.md가 없습니다.")
        sys.exit(1)

    text = ROOT_README.read_text(encoding="utf-8")

    week_title, body = extract_week_block(text)
    week_title = sanitize(week_title)
    links = extract_links(body)

    week_dir = TARGET_ROOT / week_title
    week_dir.mkdir(parents=True, exist_ok=True)

    week_readme = week_dir / "README.md"
    week_readme_content = build_weekly_readme(week_title, links)

    print(f"week_title: {week_title}")
    print(f"week_dir: {week_dir}")
    print(f"links_count: {len(links)}")

    # 주차 README 생성/업데이트
    old_week_readme = ""
    if week_readme.exists():
        old_week_readme = week_readme.read_text(encoding="utf-8")

    if old_week_readme != week_readme_content:
        week_readme.write_text(week_readme_content, encoding="utf-8")
        print(f"주차 README 생성/업데이트: {week_readme}")
    else:
        print("주차 README 변경 없음")

    # 문제 폴더 생성
    created_problem_dirs = []
    for link in links:
        problem_title, problem_url = parse_markdown_link(link)
        problem_folder_name = build_problem_folder_name(problem_title, problem_url)
        problem_dir = week_dir / problem_folder_name

        if not problem_dir.exists():
            problem_dir.mkdir(parents=True, exist_ok=True)
            created_problem_dirs.append(str(problem_dir))
            print(f"문제 폴더 생성: {problem_dir}")
        else:
            print(f"문제 폴더 이미 존재: {problem_dir}")

    if not created_problem_dirs:
        print("새로 생성된 문제 폴더 없음")


if __name__ == "__main__":
    main()
