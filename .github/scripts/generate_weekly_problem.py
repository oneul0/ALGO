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
    """
    ### 🟨 4-3주간문제 디펜스
    ~ 다음 ### 또는 ## 전까지 블록 추출
    """

    pattern = re.compile(
        r"###\s*🟨\s*(.+?)\s*\n(.*?)(?=\n###|\n## |\Z)",
        re.DOTALL
    )

    match = pattern.search(text)
    if not match:
        raise ValueError("🟨 주차 블록을 찾을 수 없음")

    title = match.group(1).strip()
    body = match.group(2)

    return title, body


def extract_links(body: str):
    links = []
    for line in body.splitlines():
        line = line.strip()
        if re.match(r"^\[.+\]\(.+\)$", line):
            links.append(line)

    if not links:
        raise ValueError("문제 링크 없음")

    return links


def build_readme(title: str, links: list[str]) -> str:
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


def main():
    if not ROOT_README.exists():
        print("README 없음")
        sys.exit(1)

    text = ROOT_README.read_text(encoding="utf-8")

    title, body = extract_week_block(text)
    title = sanitize(title)

    links = extract_links(body)

    target_dir = TARGET_ROOT / title
    target_dir.mkdir(parents=True, exist_ok=True)

    target_readme = target_dir / "README.md"
    new_content = build_readme(title, links)

    if target_readme.exists():
        old = target_readme.read_text(encoding="utf-8")
        if old == new_content:
            print("변경 없음")
            return

    target_readme.write_text(new_content, encoding="utf-8")
    print(f"생성 완료: {target_readme}")


if __name__ == "__main__":
    main()
