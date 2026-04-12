from pathlib import Path
import re
import sys

ROOT_README = Path("README.md")
TARGET_ROOT = Path("v2.2")

LANG_EXTENSIONS = {
    "java": "java",
    "swift": "swift",
    "python": "py",
    "c++": "cpp",
    "cpp": "cpp",
    "c": "c",
    "javascript": "js",
    "typescript": "ts",
    "kotlin": "kt",
}


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


def extract_member_section(text: str) -> str:
    tables = re.findall(r"<table>([\s\S]*?)</table>", text, re.IGNORECASE)

    if not tables:
        raise ValueError("README에서 table 태그를 찾을 수 없습니다.")

    for table in tables:
        if "github.com/" in table.lower():
            return table

    raise ValueError("GitHub 링크가 포함된 스터디 멤버 테이블을 찾을 수 없습니다.")


def extract_usernames_and_languages(member_section: str):
    rows = re.findall(r"<tr>([\s\S]*?)</tr>", member_section)

    if len(rows) < 3:
        raise ValueError("멤버 테이블 구조가 예상과 다릅니다. 최소 3개의 tr이 필요합니다.")

    github_row = rows[0]
    language_row = rows[2]

    usernames = re.findall(r"github\.com/([^\"/]+)", github_row, re.IGNORECASE)
    language_cells = re.findall(r"<td[^>]*>([\s\S]*?)</td>", language_row, re.IGNORECASE)

    if not usernames:
        raise ValueError("GitHub 사용자명을 찾을 수 없습니다.")

    if len(language_cells) < len(usernames):
        raise ValueError("사용자 수와 언어 셀 수가 맞지 않습니다.")

    members = []
    for idx, username in enumerate(usernames):
        cell_html = language_cells[idx]
        languages = extract_languages_from_cell(cell_html)

        if not languages:
            print(f"경고: {username}의 사용 언어를 찾지 못했습니다. 기본값 java 사용")
            languages = ["java"]

        members.append({
            "username": username,
            "languages": languages
        })

    return members


def extract_languages_from_cell(cell_html: str):
    languages = []

    badge_matches = re.findall(r"badge/([^-\s]+)-", cell_html, re.IGNORECASE)
    for badge in badge_matches:
        lang = normalize_language_name(badge)
        if lang and lang not in languages:
            languages.append(lang)

    return languages


def normalize_language_name(raw: str):
    raw_lower = raw.strip().lower()

    mapping = {
        "java": "java",
        "swift": "swift",
        "python": "python",
        "c++": "c++",
        "c%2b%2b": "c++",
        "cpp": "c++",
        "kotlin": "kotlin",
        "javascript": "javascript",
        "typescript": "typescript",
        "c": "c",
    }

    return mapping.get(raw_lower)


def ensure_file(path: Path, content: str = ""):
    if path.exists():
        return False
    path.write_text(content, encoding="utf-8")
    return True


def build_problem_readme(problem_folder_name: str, problem_url: str) -> str:
    return (
        f"# {problem_folder_name}\n\n"
        f"- 문제 링크: {problem_url}\n"
    )


def build_code_template(username: str, language: str) -> str:
    if language == "java":
        class_name = "Main"
        return (
            "import java.io.*;\n"
            "import java.util.*;\n\n"
            f"public class {class_name} {{\n"
            "    public static void main(String[] args) throws Exception {\n"
            "        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));\n"
            "    }\n"
            "}\n"
        )

    if language == "swift":
        return (
            "import Foundation\n\n"
            "// \(username)\n"
        )

    if language == "python":
        return (
            f"# {username}\n"
        )

    if language == "c++":
        return (
            "#include <bits/stdc++.h>\n"
            "using namespace std;\n\n"
            "int main() {\n"
            "    ios::sync_with_stdio(false);\n"
            "    cin.tie(nullptr);\n"
            "    return 0;\n"
            "}\n"
        )

    if language == "kotlin":
        return (
            "fun main() {\n"
            "}\n"
        )

    return ""


def main():
    if not ROOT_README.exists():
        print("루트 README.md가 없습니다.")
        sys.exit(1)

    text = ROOT_README.read_text(encoding="utf-8")

    week_title, week_body = extract_week_block(text)
    week_title = sanitize(week_title)
    links = extract_links(week_body)

    member_section = extract_member_section(text)
    members = extract_usernames_and_languages(member_section)

    print(f"week_title: {week_title}")
    print(f"links_count: {len(links)}")
    print("members:")
    for member in members:
        print(f"  - {member['username']}: {member['languages']}")

    week_dir = TARGET_ROOT / week_title
    week_dir.mkdir(parents=True, exist_ok=True)

    week_readme = week_dir / "README.md"
    week_readme_content = build_weekly_readme(week_title, links)
    if not week_readme.exists() or week_readme.read_text(encoding="utf-8") != week_readme_content:
        week_readme.write_text(week_readme_content, encoding="utf-8")
        print(f"주차 README 생성/업데이트: {week_readme}")

    for link in links:
        problem_title, problem_url = parse_markdown_link(link)
        problem_folder_name = build_problem_folder_name(problem_title, problem_url)
        problem_dir = week_dir / problem_folder_name
        problem_dir.mkdir(parents=True, exist_ok=True)

        problem_readme = problem_dir / "README.md"
        problem_readme_content = build_problem_readme(problem_folder_name, problem_url)
        if not problem_readme.exists() or problem_readme.read_text(encoding="utf-8") != problem_readme_content:
            problem_readme.write_text(problem_readme_content, encoding="utf-8")
            print(f"문제 README 생성/업데이트: {problem_readme}")

        for member in members:
            username = member["username"]
            for language in member["languages"]:
                ext = LANG_EXTENSIONS.get(language)
                if not ext:
                    print(f"지원하지 않는 언어 스킵: {username} - {language}")
                    continue

                code_file = problem_dir / f"{username}.{ext}"
                created = ensure_file(code_file, build_code_template(username, language))
                if created:
                    print(f"코드 템플릿 생성: {code_file}")
                else:
                    print(f"코드 템플릿 이미 존재: {code_file}")


if __name__ == "__main__":
    main()
